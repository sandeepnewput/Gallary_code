package com.example.gallaryapplication.view.view.model

import android.content.ContentResolver
import android.content.ContentUris
import android.provider.MediaStore
import android.util.Log
import javax.inject.Inject

class LocalApiServiceRepository @Inject constructor (private val resolver: ContentResolver) {

    fun getAllImages(): List<MediaModel> {

        val imageProjection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE
        )

        val imageSortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val cursor = resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            imageProjection,
            null,
            null,
            imageSortOrder
        )
        val imageList = ArrayList<MediaModel>()
        cursor?.use {

            if (cursor != null) {

                while (cursor.moveToNext()) {
                    try {
                        val name =
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                        val uri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                        )

                        val size =
                            cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE))

                        imageList.add(MediaModel(uri.toString(), name, size))

                    } catch (e: IllegalArgumentException) {
                        e.printStackTrace()
                    }

                }
            } else {
                Log.d("AddViewModel", "Cursor is null!")
            }
        }
        return imageList
    }//end of getAllImage

    fun getAllVideo(): List<MediaModel> {

        val videoProjection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.SIZE
        )

        val videoSortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

        val cursor = resolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            videoProjection,
            null,
            null,
            videoSortOrder
        )
        val videoList = ArrayList<MediaModel>()
        cursor?.use {

            if (cursor != null) {

                while (cursor.moveToNext()) {
                    try {
                        val name =
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))
                        val uri = ContentUris.withAppendedId(
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
                        )

                        val size =
                            cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE))

                        videoList.add(MediaModel(uri.toString(), name, size))

                    } catch (e: IllegalArgumentException) {
                        e.printStackTrace()
                    }

                }
            } else {
                Log.d("AddViewModel", "Cursor is null!")
            }
        }
        return videoList
    }//end of getAllvideo function


    fun getAllMusic(): List<MediaModel> {

        val musicProjection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.SIZE
        )

        val musicSortOrder = "${MediaStore.Audio.Media.DATE_ADDED} DESC"

        val cursor = resolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            musicProjection,
            null,
            null,
            musicSortOrder
        )
        val musicList = ArrayList<MediaModel>()
        cursor?.use {

            if (cursor != null) {


                while (cursor.moveToNext()) {
                    try {
                        val name =
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))

                        val uri = ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
                        )

                        val size =
                            cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))

                        musicList.add(MediaModel(uri.toString(), name, size))

                    } catch (e: IllegalArgumentException) {
                        e.printStackTrace()
                    }

                }
            } else {
                Log.d("AddViewModel", "Cursor is null!")
            }
        }
        return musicList
    }//end of getAllImage


}//end of GallaryApiServiceRepository