FROM openjdk

RUN mkdir -p /Users/Admin/IdeaProjects/FirstCook/app
WORKDIR /Users/Admin/IdeaProjects/FirstCook/app

COPY . /Users/Admin/IdeaProjects/FirstCook/app



CMD ["openjdk", "FirstCook-0.0.1-SNAPSHOT.jar"]

