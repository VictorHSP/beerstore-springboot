/**
  Configurando a versão do provider e selecionando um profile
*/

provider "aws" {
  version = "~> 1.38"
  shared_credentials_file = "~/.aws/credentials"
  profile = "terraform"
}