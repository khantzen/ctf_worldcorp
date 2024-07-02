FROM kalilinux/kali-rolling:latest

RUN mkdir /formation

COPY packages.txt /formation/packages.txt

WORKDIR /formation

RUN apt update && apt install -y $(cat packages.txt) && wget https://github.com/DominicBreuker/pspy/releases/download/v1.2.1/pspy64

COPY ./wordlists /formation/wordlists

