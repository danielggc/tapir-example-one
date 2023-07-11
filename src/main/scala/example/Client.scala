package example


object Client {
  def main(args: Array[String])= {
      print("iniciando cliente")
      EndPointsBooks.makeClientRequest()
  }
}
