# MyCode API

## Setup der Webseite
- Datenbank?
- Backendserver?

## Abstract

TODO: Kurze Beschreibung des Projektes

## Zielpublikum

TODO: Wer ist das Zielpublikum?

## Sitemap

TODO: Erreichbare Seiten

## Navigation

TODO: Wie ist navigation aufgebaut?

## Design

### Adobe XD (Falls vorhanden)
TODO

### Farbschema

TODO: Welches Farbschema wurde verwendet?

### Schriftarten

Verwendete Schriftarten: KronaOne-Regular

# Testing
-	Betriebssystem: Windows 11 Home
-	Browser: Firefox
-	Gerät: HP Spectre x360 16 --> CPU: 13th Gen Intel Core i7-1360P, RAM: 32GB DDR4 3200, Storage: 1TB
-	Dimensionen: 40.6cm diagonal
-   Auflösung: 3840 x 2400

### User acceptance tests

#### Test 1

**Vorbedigung**

T-03 muss erfüllt sein (Fotos der Fotogalerie «Tastaturen» werden angezeigt).

**Ablauf**

Klick auf «Produkte» im Hauptmenu, dann Klick auf «Tastaturen» im Submenu. F12 drücken und im responsive Modus «Galaxy S5» auswählen.

**Resultat**

Bilder der verschiedenen Tastaturen werden untereinander dargestellt (eine Spalte).


# Fazit
TODO:
- Was lief gut/schlecht?
- Hast du deine Ziele erreicht? 
- Wie bist du mit dem Endergebnis zufrieden?
- Was hast du gelernt?
- Was würdest du nächstes Mal anders machen?


# How to start the website

## Prerequisites

- Docker has to be installed (We will be using Docker Desktop for this example): [Install Docker Desktop](https://www.docker.com/products/docker-desktop/)
- Nodejs has to be installed: [Install Nodejs](https://nodejs.org/en/download/current)
- Git has to be installed: [Install Git](https://git-scm.com/downloads)

## Step-by-step instructions

#### Clone the Projekt repository

- Open a terminal
- Execute the following command: `git clone ssh://git@git.bbcag.ch:2222/inf-bl/zh/2023/team-c/zkampm/MyCode_restAPI.git`
- If you don't have an ssh key: `git clone https://git.bbcag.ch/inf-bl/zh/2023/team-c/zkampm/MyCode_restAPI.git`

#### Install the required modules

##### In the Commandline or Visual Studio Code Terminal:

- Navigate to the client folder of the project
- Execute the following command: `npm install`
- Navigate to the server folder of the project
- Execute the following command: `npm install`

- If you get errors when executing the `npm install` command, you might have to execute the `npm init` command first

#### Set up the Docker connection

##### In the Commandline or Visual Studio Code Terminal:

- Navigate to the "server" folder of the project
- Execute the following command: `docker compose up`

#### Start the project

##### In the Docker Dektop app:

- Navigate to your docker containers:
![Docker Navigation](pictures\docker_navigate.png)

- Start the container with the name "server":
![Docker start container](pictures\docker_start_container.png)

##### In the Commandline or Visual Studio Code Terminal:

- Navigate to the "server" folder of the project
- Execute the following command: `npm start`
```bash
C:\MyCode_restAPI\server> npm start
```

- Navigate to the "client" folder of the project
- Execute the following command: `npm run dev`
```bash
C:\MyCode_restAPI\client> npm run dev
```

##### In your Browser:

- Navigate to http://localhost:3000



# Troubleshooting (Windows 11)

##### Problem:

I get an error when trying run the `docker compose up` command:
![Docker Compose Error](pictures\docker_compose_error.png)

##### Solution:

- Open the Services in the Windows startmenu (Windows button):
![Services in startmenu](pictures\open_services.png)

- Scroll down until you find the MySQL Service:
![Services MySQL80](pictures\services_MySQL80.png)

- Right-click on the MySQL service and stop it:
![Stop MySQL service](pictures\services_stopMySQL80.png)

- Right-click again and choose the "properties" option:
![Services MySQL properties](pictures\services_properties.png)

- Change the startup type to "Manual":
![MySQL service startup type](pictures\services_properties_startup-type.png)
