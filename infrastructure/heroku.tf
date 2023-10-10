resource "heroku_app" "rent-your-stuff-emerald" {
  name   = "rent-your-stuff-emerald"
  region = "eu"

  config_vars = {
    FOOBAR = "baz"
  }

  buildpacks = [
    "heroku/gradle"
  ]
}