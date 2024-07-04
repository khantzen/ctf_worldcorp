Tooling
---


## Liste outils hacking 

Une liste d'outil possible utilisable pour résoudre les différentes vm

- [ffuf](https://github.com/ffuf/ffuf) un Fuzzer multi-os permettant de rechercher des infos sur différents sites.
- [John the Ripper](https://www.openwall.com/john/) Un soft de bruteforce pour découvrir des mots de passe.
- [Hashsuite](https://hashsuite.openwall.net/) comme John mais pour window.
- [ZapProxy](https://www.zaproxy.org/) Un scanner de site web.
- [Postman](https://www.postman.com/downloads/) Un soft permettant de jouer des requêtes http.
- [pspy64](https://github.com/DominicBreuker/pspy) Permet de monitorer les process linux sans être root.
- [nmap](https://nmap.org/) Un mapper de réseau permettant de scanner une ip.
- [Sherlock](https://github.com/sherlock-project/sherlock) un chasseur de nom d'utilisateur sur réseaux sociaux.
- [SqlMap](https://salsa.debian.org/pkg-security-team/sqlmap) permet de faire un mapping de base depuis une injection sql.

- [Linux command cheat-sheet](https://www.geeksforgeeks.org/linux-commands-cheat-sheet/)

### Image docker

Une image docker est disponible `kha91/ctf-tooling-worldcorp:202407041130` elle contient les outils de la liste précédente exécutables via ligne de commande, ainsi que les listes de mots du dossier `wordlists` de ce dépôt.

L'image peut se récupérer depuis dockerhub

```bash
docker pull kha91/ctf-tooling-worldcorp:202407041130
```

Vous pouvez également la construire vous même depuis ce dépot git.

```bash
docker built -it ctf-tooling-worldcorp:my-version .
```

Créer un dossier `my-data` sur votre pc et lancez le conteneur via la commande suivante

```bash
docker run --rm -it \
  -v /path/vers/mon/dossier/my-data:/formation/my-data \
  kha91/ctf-tooling-worldcorp:202407041130 \
  bash
```

Pour voir les outils pré-installés dans l'image

```bash
cat packages.txt
```

Ainsi tout ce qui sera sauvegardé dans `/formation/my-data` depuis le conteneur sera persisté sur votre pc.

> Vous pouvez installer n'importe quel [package kali](https://www.kali.org/tools/) dans le conteneur via la commande `apt install <package name>`

> :warning: Si vous quittez le conteneur il faudra relancer la commande

## Comment faire un scp

```bash
scp {path à copier} {path où coller}
```

Si le path est en local, renseigner simplement le path local

Si le path est sur une machine distance, renseigner `user@ip_machine:/le/path/du/fichier`

Pour copier de ma machine vers une machine à distance

```bash
scp /le/path/a/copier user@111.111.111.111:/le/path/ou/coller
```

Pour copier la machine distante vers ma machine locale

```bash
scp user@111.111.111.111:/le/path/a/copier /le/path/ou/coller
```

## Rendre un script exécutable

```bash
chmod +x /path/vers/le/script
```

## Cheatsheet git

https://education.github.com/git-cheat-sheet-education.pdf
