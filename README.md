# About project

## Simple Spring Boot project based on Mongo, Redis and Docker Compose

### How to build and run application

1. Clone sources of [project](https://github.com/NadiaLogvinova/cdq-recruitment-task.git) from GitHub:
```bash
git clone https://github.com/NadiaLogvinova/cdq-recruitment-task.git
```

2. You also need Docker. See https://docs.docker.com/installation/#installation for details on setting Docker up for your machine. Before proceeding further, verify you can run docker commands from the shell.

3.  Build and run the application, Mongo and Redis containers using the command (run from the Intellij IDEA terminal or from the root of project command line)
```bash
docker-compose up
```
4. Go to <http://localhost:8080>. You will see swagger UI with application API. 

5. To stop the application, use Ctrl + C in the terminal or this command from the command line:
```bash
docker-compose down
```