terraform {

  required_providers {
    heroku = {
      source = "heroku/heroku"
      version = "~> 5.0"
    }

    herokux = {
      source = " davidji99/herokux"
      version = "~> 1.0.0"
    }
  }

  backend "remote" {
    organization = "mossabdeh"

    workspaces {
      name = "rent-your-stuff"
    }

  }
  required_version = ">=1.1.3"

}

provider "heroku" {
  email = var.heroku_email
  api_key = var.heroku_api_key
}

provider "herokux" {
  api_key = var.heroku_api_key
}