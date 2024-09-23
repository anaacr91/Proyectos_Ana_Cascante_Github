
const { defineConfig } = require("cypress");
const preprocessor = require("@badeball/cypress-cucumber-preprocessor");
const browserify = require("@badeball/cypress-cucumber-preprocessor/browserify");
const sqlServer = require("cypress-sql-server");

async function setupNodeEvents(on, config) {

  config.db= {
    userName: 'postgres',
    password: 'GtHty6K3hoROMqzpi7UrRbGh9B',
    server: 'Digimevo_local',
    options:{
      database: 'testCypress',
      encrypt: true,
      trustCollectionOnRequestCompletion: true
    }
  }

  // implement node event listeners here
  await preprocessor.addCucumberPreprocessorPlugin(on, config);
  on('file:preprocessor', browserify.default(config));

  // Load the sql server plugin
  tasks = sqlServer.loadDBPlugin(config.db);
  on('task', tasks);

  return config;
};

module.exports = defineConfig({

  env: { //we define the environment variables
    url: 'https://rahulshettyacademy.com',
    
  },

  e2e: {
    setupNodeEvents,
    specPattern: 'cypress/integration/examples/*.js',
    experimentalRunAllSpecs: true,
    defaultCommandTimeout: 6000, // 6 seconds to wait for a command to finish before failing
  },
});
