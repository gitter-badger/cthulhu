package org.cthulhu.service.google.drive

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.{Drive, DriveScopes}
import play.api.Play
import play.api.Play.current

/**
  * Created by rogersepulveda on 12/18/15.
  */
class GoogleDriveServiceImpl extends GoogleDriveService {

  private lazy val credentials = new GoogleCredential.Builder()
    .setTransport(Constants.HttpTransport)
    .setJsonFactory(Constants.JsonFactory)
    .setServiceAccountId("p12-test@focus-fold-94417.iam.gserviceaccount.com")
    .setServiceAccountPrivateKeyFromP12File(new java.io.File(Play.application.configuration.getString("com.cthulhu.drive.p12file").get))
    .setServiceAccountScopes(DriveScopes.all())
    .build()

  private lazy val drive = new Drive.Builder(Constants.HttpTransport, Constants.JsonFactory, credentials)
    .setApplicationName("fun-notes")
    .build()

  def execute [T](f: Drive => T): T = f(drive)

  object Constants {
    val JsonFactory = JacksonFactory.getDefaultInstance
    val HttpTransport = GoogleNetHttpTransport.newTrustedTransport()
  }
}
