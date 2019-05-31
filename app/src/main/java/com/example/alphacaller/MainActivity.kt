package com.example.alphacaller

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.budiyev.android.circularprogressbar.CircularProgressBar
import org.apache.poi.openxml4j.opc.OPCPackage
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    var numberData: Database?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Make directory for storage of excel file
        this.getExternalFilesDir(null).mkdirs()

        //Initialize all ButtonViews
        val startCallButton: Button = findViewById(R.id.startCallingButton)
        val clearDataButton: Button = findViewById(R.id.clearDatabaseButton)
        val importButton: Button = findViewById(R.id.importButton)

        numberData = Database(this@MainActivity)

        refreshTextAndProgress() //Give all UI items their initial states

        if (startCallButton.isClickable && startCallButton.isEnabled) {
            startCallButton.setOnClickListener {
                refreshTextAndProgress()
                Handler().postDelayed({
                    val startCall = Intent(this@MainActivity, CallScreen::class.java)
                    startActivity(startCall)
                    this.finish()
                }, 2000)
            }
        }

        if (clearDataButton.isClickable && clearDataButton.isEnabled) {
            clearDataButton.setOnClickListener {
                numberData?.deleteData()
                refreshTextAndProgress()
            }

            importButton.setOnClickListener {
                readExcel(this, "test.xlsx")
                Toast.makeText(this@MainActivity, "Done Importing!", Toast.LENGTH_SHORT).show()
                refreshTextAndProgress()
            }
        }
        if (importButton.isClickable && importButton.isEnabled) {
            importButton.setOnClickListener {
                readExcel(this, "test.xlsx")
                Toast.makeText(this@MainActivity, "Done Importing!", Toast.LENGTH_SHORT).show()
                refreshTextAndProgress()
            }

            startCallButton.setOnClickListener {
                refreshTextAndProgress()
                Handler().postDelayed({
                    val startCall = Intent(this@MainActivity, CallScreen::class.java)
                    startActivity(startCall)
                    this.finish()
                }, 2000)
            }

            clearDataButton.setOnClickListener {
                numberData?.deleteData()
                refreshTextAndProgress()
            }
        }
    }

    fun refreshTextAndProgress() //Utility function to Update Progress bar and Text of all TextViews
    {
        val progressBar: CircularProgressBar = findViewById(R.id.progress_bar)
        val contactNumberLabel: TextView = findViewById(R.id.contactNumberLabel)
        val startCallButton: Button = findViewById(R.id.startCallingButton)
        val importButton: Button = findViewById(R.id.importButton)
        val clearDataButton: Button = findViewById(R.id.clearDatabaseButton)
        val numberList = numberData?.queryDB()
        val numbers: Int

        if(numberList?.size == null) {
            Toast.makeText(this@MainActivity, "Database is Empty! Load Data...", Toast.LENGTH_SHORT).show()
            numbers = 0
            progressBar.progress = 0f
            startCallButton.isClickable = false
            startCallButton.isEnabled = false
            clearDataButton.isEnabled = false
            clearDataButton.isClickable = false
            importButton.isClickable = true
            importButton.isEnabled = true
        }
        else
        {
            numbers = numberList.size
            progressBar.progress = 65f
            progressBar.animate()
            startCallButton.isClickable = true
            startCallButton.isEnabled = true
            clearDataButton.isEnabled = true
            clearDataButton.isClickable = true
            importButton.isClickable = false
            importButton.isEnabled = false
        }
        val message: String = String.format("Contacts Loaded: %1$2s", numbers)
        contactNumberLabel.text = message
    }

    private fun readExcel(context: Context, filename: String) //function to read Excel file and import data into SQLite Database
    {
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly())
        {
            Toast.makeText(this, "Storage not available or is read only", Toast.LENGTH_SHORT).show()
            return
        }

        try
        {
            val file = File(context.getExternalFilesDir(null)?.toString(), filename)
            val pkg = OPCPackage.open(file)
            val myWorkBook = XSSFWorkbook(pkg)

            // Get the first sheet from workbook
            val mySheet = myWorkBook.getSheetAt(0)

            // We now need something to iterate through the cells.
            val rowIterator = mySheet.rowIterator()

            while(rowIterator.hasNext())
            {
                val myRow = rowIterator.next() as XSSFRow
                val cellIterator = myRow.cellIterator()
                while(cellIterator.hasNext())
                {
                    val myCell = cellIterator.next() as XSSFCell
                    numberData?.storeNumber(myCell.toString()) //add data of current cell to the database
                }
            }
            pkg.close()
        }
        catch(e: Exception)
        {
            e.printStackTrace()
        }
        return
    }
    private fun isExternalStorageReadOnly(): Boolean //function to check if External Storage is Read Only
    {
        val extStorageState = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState)
        {
            return true
        }
        return false
    }
    private fun isExternalStorageAvailable(): Boolean //function to check if External Storage is Available
    {
        val extStorageState = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == extStorageState)
        {
            return true
        }
        return false
    }
}
