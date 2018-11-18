provider "aws" {

  version = "~> 1.38"
  shared_credentials_file = "/root/.aws/credentials"
  profile = "terraform"
}