package model

case class FileUploadInfo(
                           val resumableChunkNumber: Int,
                           val resumableChunkSize: Int,
                           val resumableTotalSize: Long,
                           val resumableIdentifier: String,
                           val resumableFilename: String
                         ) {
  def totalChunks = Math.ceil(resumableTotalSize.toDouble / resumableChunkSize.toDouble)
}