resource "heroku_app" "rent-your-stuff-staging" {
  name   = "rent-your-stuff-staging"
  region = "eu"

  config_vars = {
    FOOBAR = "baz"
  }

  buildpacks = [
    "heroku/go"
  ]
}