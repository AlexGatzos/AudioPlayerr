# UniversityMusic

## Development

Αρχικά πρέπει να υπάρχουν εγκατεστημένα τα:
- [Java](https://www.java.com/en/download/help/index_installing.html)
- [Maven](http://maven.apache.org/)
- [Docker](https://www.docker.com/get-started)

Για MacOS αν χρησιμοποιείται το homebrew

```sh
$ brew update

$ brew cask install java

$ brew install maven

$ brew cask install docker
```

Μετά ανοίγουμε ένα terminal στο φάκελο που βρίσκεται η εφαρμογή και τρέχουμε τα εξής:


```sh
make setup
```

Εγκαθιστά τα dependencies και δημιουργεί τη βάση και τους πίνακες. 

---

```sh
make dev
```

Για να τρέξει την postgres, να κάνει compile το κώδικα και να τον τρέξει ώστε να δούμε το αποτέλεσμα.
