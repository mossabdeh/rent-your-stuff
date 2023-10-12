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

# Create a database, and configure the app to use it
resource "heroku_addon" "rent-your-stuff-emerald_db" {
  app_id = heroku_app.rent-your-stuff-emerald.id
  plan   = "heroku-postgresql:mini"
}

# Create a Pipeline
resource "heroku_pipeline" "rent-your-stuff-pipeline" {
  name = "rent-your-stuff-pipeline"
}

# Coupling app to pipeline
resource "heroku_pipeline_coupling" "staging_pipeline_coupling" {
  app_id   = heroku_app.rent-your-stuff-emerald.id
  pipeline = heroku_pipeline.rent-your-stuff-pipeline.id
  stage    = "staging"
}

// Add the GitHub repository integration with the pipeline.
resource "herokux_pipeline_github_integration" "foobar" {
  pipeline_id = heroku_pipeline.rent-your-stuff-pipeline.id
  org_repo = "mossabdeh/rent-your-stuff"
}

// Add Heroku app GitHub integration.
resource "herokux_app_github_integration" "foobar" {
  app_id = heroku_app.rent-your-stuff-emerald.uuid
  branch = "main"
  auto_deploy = true
  wait_for_ci = true

  # Tells Terraform that this resource must be created/updated
  # only after the `herokux_pipeline_github_integration` has been successfully applied .
  depends_on = [herokux_pipeline_github_integration.foobar]
  # i authorize my github account to heroku
  # i set java version to 17 in gradle build to solve heroku log problem
}

