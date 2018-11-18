terraform {
  backend "s3" {
    bucket = "beerstore-terraform-state"
    key = "beerstore-curso-online"
    region = "us-east-1"
    profile = "terraform"
  }
}