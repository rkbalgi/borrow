```bash

$ docker buildx build --platform linux/amd64 -t registry.heroku.com/borrow-app-ab/web .
$ docker push registry.heroku.com/borrow-app-ab/web
$ heroku container:release web -a borrow-app-ab

```
