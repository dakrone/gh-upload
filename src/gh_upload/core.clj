(ns gh-upload.core
  (:require [clojure.java.io :refer [file]]
            [tentacles.repos :as repo])
  (:gen-class))

(defn get-user []
  (or (System/getenv "GHUSER")
      (throw (Exception. "Need $GHUSER set to Github username."))))

(defn get-password []
  (or (System/getenv "GHPASS")
      (throw (Exception. "Need $GHPASS set to Github password."))))

(defn get-repo-owner []
  (or (System/getenv "GHREPOOWNER")
      (throw (Exception. "Need $GHREPOOWNER set to Github repository owner."))))

(defn get-repo []
  (or (System/getenv "GHREPO")
      (throw (Exception. "Need $GHREPO set to Github repository."))))

(defn delete-if-exists [filename]
  (println "Checking if file already exists...")
  (let [user (get-user)
        owner (get-repo-owner)
        repo (get-repo)
        opts {:auth (str user ":" (get-password))}
        basename (.getName (file filename))
        downloads (repo/downloads owner repo opts)
        id (first (map :id (filter #(= (:name %) basename) downloads)))]
    (when id
      (println (str "Deleting existing file (" id ")..."))
      (repo/delete-download owner repo id opts))))

(defn upload-file [filename]
  (let [user (get-user)
        owner (get-repo-owner)
        repo (get-repo)
        opts {:auth (str user ":" (get-password))}
        _ (delete-if-exists filename)
        resp (repo/download-resource owner repo filename opts)]
    (println (str "Uploading file " filename "..."))
    (repo/upload-file resp)))

(defn -main [& args]
  (if (first args)
    (do (upload-file (first args))
        (println "Done."))
    (println "Need a file to upload.")))
