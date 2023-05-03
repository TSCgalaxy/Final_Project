package com.example.finalproject

import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.*
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.finalproject.ui.theme.FinalProjectTheme


import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView


import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.graphics.Canvas
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalContext

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.PermissionChecker
import java.io.*

import android.graphics.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import java.io.File
import java.io.FileOutputStream
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //FileReadWrite()
                    requestPermission(LocalContext.current)
                    DndApp()

                }

            }
        }
    }


    class ProfileCardView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : AbstractComposeView(context, attrs, defStyleAttr) {
        @Composable
        override fun Content() {
            // This is a ComposableUI function
            DndApp()
            Text("Hello World")
            Log.d("called profileCardView", "works")
        }
    }

    private var jetCaptureView: MutableState<ProfileCardView>? = null

    @Composable
    fun ProfileUI() {
        jetCaptureView = remember { mutableStateOf(ProfileCardView(this@MainActivity)) }
        AndroidView(modifier = Modifier.wrapContentSize(),
            factory = {
                ProfileCardView(it).apply {
                    post {
                        jetCaptureView?.value = this
                    }
                }
            }
        )
    }

    class ImageUtils {

        companion object{
            fun generateBitmap(view: View): Bitmap {
                val bitmap = Bitmap.createBitmap(
                    view.width,
                    view.height,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                view.layout(
                    view.left,
                    view.top,
                    view.right,
                    view.bottom
                )
                view.draw(canvas)
                return bitmap
            }
        }
    }

        /*override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            setContent {
                FileRWInternalStorageJetpackComposeTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        FileReadWrite()
                    }
                }
            }
        }*/
        private fun checkPermission(context: Context):Boolean {

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Environment.isExternalStorageManager()
            } else {
                val readFile=ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
                val writFile=ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
                readFile && writFile
            }

        }
        private fun requestPermission(context: Context){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val intent =Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data=Uri.parse(String.format("package:%s",applicationContext.packageName))
                context.startActivity(intent)
            } else {
                val requestReadWritePermissionLaucher=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
                        result ->
                    var count:Int=0
                    result.entries.forEach {
                        if (it.value==true) {
                            count++
                        }
                    }
                    if (count==2) {
                        Toast.makeText(context,"Permission Granted",Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context,"Permission not Granted",Toast.LENGTH_SHORT).show()
                    }

                }
                requestReadWritePermissionLaucher.launch(arrayOf(READ_EXTERNAL_STORAGE,
                    WRITE_EXTERNAL_STORAGE))
            }
        }

    private fun writeToFile(context: Context, fileName: String, content: String) {

            val f = File(Environment.getExternalStorageDirectory(), fileName)
            if (!f.exists()) {
                f.createNewFile()
            }
            val fileWriter = BufferedWriter(FileOutputStream(f).bufferedWriter())
            fileWriter.write(content)
            fileWriter.close()

        }

    private fun readFromFile(context: Context, fileName: String): String {
        val data: StringBuffer = StringBuffer()
        val f = File(Environment.getExternalStorageDirectory(), fileName)
        if (!f.exists()) {
            Toast.makeText(context, "File not Found", Toast.LENGTH_SHORT).show()

        } else {
            val fileReader = BufferedReader(FileInputStream(f).bufferedReader())
            var line = fileReader.readLine()
            while (line != null) {
                data.append(line)
                line = fileReader.readLine()
            }
            fileReader.close()
        }
        return data.toString()
    }

    @Composable
    fun FileReadWrite() {
        val data = remember { mutableStateOf("") }
        val context = LocalContext.current
        val fileName = remember { mutableStateOf("") }
        val content = remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "File Reading & Writing Internal Storage", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = fileName.value,
                onValueChange = {
                    fileName.value = it
                },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        tint = Color.Red,
                        contentDescription = "username"
                    )
                },
                placeholder = {
                    Text("Enter FileName", color = Color.Red)
                },
                label = {
                    Text(text = "Write File Name Here", color = Color.Red)
                },
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = content.value, onValueChange = {
                    content.value = it
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        tint = Color.Red,
                        contentDescription = "filecontent"
                    )
                },
                placeholder = {
                    Text(text = "Write File Content Here", color = Color.Red)
                },
                label = {
                    Text(text = "Write File Content Here", color = Color.Red)
                },
                maxLines = 4
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(onClick = {
                    if (fileName.value.isNotEmpty() && content.value.isNotEmpty()) {
                        if (checkPermission(context)) {
                            writeToFile(context, fileName.value, content.value)
                            Toast.makeText(context, "File Created", Toast.LENGTH_SHORT).show()
                        } else {
                            requestPermission(context = context)
                        }
                    }
                    else {
                        Toast.makeText(context, "Write File Name and Data", Toast.LENGTH_SHORT)
                            .show()
                    }

                })
                {
                    Text(text = "Write to File", fontWeight = FontWeight.Bold)

                }
                Spacer(modifier = Modifier.width(20.dp))
                OutlinedButton(onClick = {
                    if (fileName.value.isNotEmpty()) {
                        if (checkPermission(context)) {
                            data.value = readFromFile(context, fileName.value)
                        } else {
                            requestPermission(context = context)
                        }}
                    else {
                        Toast.makeText(context, "File Not Found", Toast.LENGTH_SHORT).show()
                    }

                }, modifier = Modifier.background(color = Color.Blue))
                {
                    Text(text = "Read From File", fontWeight = FontWeight.Bold)
                }

            }
            Text(
                text = "File Data:\n" + data.value, modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

        }
    }



//   AndroidView({ context -> TextView(context).apply { text = "This is a TextView" } })
}
@Composable
fun test() {
    var document = PdfDocument()
    var  file = File(Environment.getExternalStorageDirectory(), "Charcter.pdf")

    // create a page description
    var width : Int = (8 * 72) as Int
    var length : Int = (11 * 72) as Int
    var pageInfo = PageInfo.Builder(width, length, 1).create()

    // start a page
    var page = document.startPage(pageInfo)

    // draw something on the page
    var content = MainActivity.ProfileCardView(context = LocalContext.current)
//    content.Content()
    content.draw(page.getCanvas())
    // finish the page
    document.finishPage(page)
    // write the document content
    //val out = OutputStreamWriter
    //document.writeTo(getOutputStream())
    // close the document
    document.writeTo(FileOutputStream(file, false))
    document.close()
    Log.d("document.toString()", document.pages.toString())
    Log.d("file", file.path)
}


@Composable
fun test2() {
    var document = PdfDocument()
    var  file = File(Environment.getExternalStorageDirectory(), "Charcter.pdf")

    // create a page description
    var width : Int = (8 * 72) as Int
    var length : Int = (11 * 72) as Int
    var pageInfo = PageInfo.Builder(width, length, 1).create()

    // start a page
    var page = document.startPage(pageInfo)

    // draw something on the page
    var content = MainActivity.ProfileCardView(context = LocalContext.current)
//    content.Content()
    var canva = page.canvas
    var title: Paint = Paint()
    title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))

    title.textSize = 15F

    title.setColor(ContextCompat.getColor(LocalContext.current, R.color.purple_200))

    canva.drawText("A portal for IT professionals.", 209F, 100F, title)
    canva.drawText("Geeks for Geeks", 209F, 80F, title)
    title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
    title.setColor(ContextCompat.getColor(LocalContext.current, R.color.purple_200))
    title.textSize = 15F

    // below line is used for setting
    // our text to center of PDF.
    title.textAlign = Paint.Align.CENTER
    canva.drawText("This is sample document which we have created.", 396F, 560F, title)

    //content.draw(page.getCanvas())
    // finish the page
    document.finishPage(page)
    // write the document content
    //val out = OutputStreamWriter
    //document.writeTo(getOutputStream())
    // close the document
    document.writeTo(FileOutputStream(file, false))
    document.close()
    Log.d("document.toString()", document.pages.toString())
    Log.d("file", file.path)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}

