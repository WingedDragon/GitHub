package com.example.dong.github.Util

import com.example.dong.github.APP
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.nio.file.Files.exists
import java.nio.file.Files.exists





/**
 * Created by 振兴 on 2018.3.17.
 */
object FileManage {

    var hasInit = false

    val BasePath = APP.getContext().getExternalFilesDir(null).path + "/project"

    fun initFilePath(){
        if(PermissionManage.hasWriteStorage){
            val basedir = File(BasePath)
            if(!basedir.exists()){
                basedir.mkdir()
            }
        }
    }

    fun Unzip(zipFile:String, targetDir:String){
        val buffer = 4096
        var strEntry: String

        try {
            var BOS:BufferedOutputStream? = null
            val FIS = FileInputStream(zipFile)
            val ZIS = ZipInputStream(FIS)
            var entry:ZipEntry? = null

            entry = ZIS.nextEntry
            while (entry != null){
                try {
                    var count = 0
                    val data = ByteArray(buffer)
                    strEntry = entry.name

                    val entryFile = File(targetDir + strEntry)
                    val entryDir = File(entryFile.parent)

                    if(!entryDir.exists()){
                        entryDir.mkdirs()
                    }

                    val FOS = FileOutputStream(entryFile)
                    BOS = BufferedOutputStream(FOS,buffer)
                    count = ZIS.read(data,0,buffer)
                    while (count != -1){
                        BOS.write(data,0,count)
                        count = ZIS.read(data,0,buffer)
                    }
                    BOS.flush()
                    BOS.close()
                }catch (e:Exception){
                    e.printStackTrace()
                }
                entry = ZIS.nextEntry
            }
            ZIS.close()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}