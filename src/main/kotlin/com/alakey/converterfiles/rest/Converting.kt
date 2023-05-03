package com.alakey.converterfiles.rest

import com.alakey.converterfiles.service.DocxToHtmlConverter
import com.alakey.converterfiles.service.FileJson
import com.alakey.converterfiles.service.HtmlToPdfConverter
import org.springframework.web.bind.annotation.*
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/converting/")
class Converting {

    val docxToHtmlConverter = DocxToHtmlConverter()
    val htmlToPdfConverter = HtmlToPdfConverter()

    //Converting docx to html using LibreOffice
    @GetMapping("convertDocxToHtml")
    fun convertDocxToHtml(
        @RequestBody fileJson: FileJson
    ): String {

        val currentDate = LocalDateTime.now()
        val currentYear = currentDate.year
        val currentMonth = currentDate.monthValue
        val currentDay = currentDate.dayOfMonth

        val directory = File("files/$currentYear/$currentMonth/$currentDay")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val file = File(directory.toString() + "/" + fileJson.fileName)
        val outputStream = FileOutputStream(file)
        outputStream.write(fileJson.byteArray)
        outputStream.close()
        return docxToHtmlConverter.docxToHtml(file, directory.toString())
    }

    //Convert html to pdf
    @GetMapping("convertHtmlToPdf")
    fun convertHtmlToPdf(
        @RequestBody fileJson: FileJson
    ): ByteArray {
        val currentDate = LocalDateTime.now()
        val currentYear = currentDate.year
        val currentMonth = currentDate.monthValue
        val currentDay = currentDate.dayOfMonth

        val directory = File("files/$currentYear/$currentMonth/$currentDay")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val file = File(directory.toString() + "/" + fileJson.fileName)
        val outputStream = FileOutputStream(file)
        outputStream.write(fileJson.byteArray)
        outputStream.close()
        return htmlToPdfConverter.htmlToPdf(file, directory.toString())
    }

    //Очистка директории
    @PostMapping("deleteDirectory")
    fun deleteDirectory(): String {
        val directory = File("files/")
        return if (directory.exists()) {
            directory.deleteRecursively()
            "Успех"
        } else
            "Директории не существует"
    }
}