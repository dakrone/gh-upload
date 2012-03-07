# gh-upload

```zsh
wget https://github.com/downloads/dakrone/gh-upload/gh-upload-1.0-standalone.jar

export GHUSER=dakrone
export GHREPO=cheshire
export GHPASS=mysupersecretpassword

java -jar gh-upload-1.0-standalone.jar /path/to/file.data
```

## why?

There were a lot of tools for uploading a file to github, but none
that were standalone java utils.

## known issues

Won't work with openJDK 7, because of cert issues.
