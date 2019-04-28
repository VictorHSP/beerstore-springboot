/**
  Armazena o estado do terraform no S3
*/

terraform {
  backend "s3" {
    bucket = "beerstore-terraform-state"
    key = "beerstore"
    region = "us-east-1"
    profile = "terraform"
  }
}