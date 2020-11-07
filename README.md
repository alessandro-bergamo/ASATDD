<br />
<p align="center">
  
  <h3 align="center">Indentify SATD</h3>

  <p align="center">
    Identify SATD è un plugin per IntelliJ utile all'identificazione dei "Self Admitted Technical Debt"
    <br />
    <a href="https://github.com/alessandro-bergamo/Indentify-SATD"><strong>Esplora il codice »</strong></a>
    <br />
    <br />
    ·
    <a href="https://github.com/alessandro-bergamo/Indentify-SATD/issues">Segnala Bug</a>
    ·
    <a href="https://github.com/alessandro-bergamo/Indentify-SATD/issues">Richiedi Feature</a>
  </p>
</p>



<!-- TABLE OF CONTENTS -->
## Guida al README

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Instructions](#instructions)
* [Decisions](#decisions)
* [Contact](#contact)



<!-- ABOUT THE PROJECT -->
## About The Project

<h3>Indentify SATD</h3>

Un "Self Admitted Technical Debt" è un modo per segnalare di aver pubblicato del codice incompleto, che richiede un rifacimento, che produce errori o che è giusto per far funzionare l'intero sistema.
Questo plugin mira ad identificarli attraverso l'analisi testuale del "Commit Message", disponibile dopo la pubblicazione del codice al "master" branch.


### Built With
Il plugin è realizzato interamente in:
* [JetBrains IntelliJ](https://www.jetbrains.com/idea/)
* [Java](https://www.java.com/)
* [Weka](https://www.cs.waikato.ac.nz/ml/weka/)

<!-- Instructions -->
## Instructions

Il plugin si sofferma su pochi ed incisivi passi, tra cui:

1. Clone della repository github e recupero dei commit message
2. Analisi testuale di ogni commit message
3. Identificazione delle possibili "keywords" potenzialmente rappresentanti dei SATD
4. Segnalazione del Commit ID e del commit message identificato al passo precedente

<!-- Decisions -->
## Decisions

* 22/10/2020 

Durante le fasi primordiali di sviluppo si è deciso di strutturare l'intero plugin con due moduli ben distinti.

Un primo modulo presenta il core esecutivo del plugin, ovvero tutto il codice riguardante l'identificazione dei SATB.
Il secondo modulo presenta la parte di interazione tra il core del plugin con l'IDE stesso, quindi va a gestire tutti gli input e output.

Questa scelta è stata presa per rendere il core del plugin ben separato dall'IDE in cui è stato sviluppato il plugin, così da renderlo utile e soprattutto riutilizzabile con altri IDE al di fuori di IntelliJ, mantenendo la parte di logica del software separata dalla parte di input/output.




<!-- CONTACT -->
## Contact

Your Name - [@linkedin](https://www.linkedin.com/in/alessandro-bergamo-4a21b11ba/) - a.bergamo2@studenti.unisa.it

Project Link: [https://github.com/alessandro-bergamo/Indentify-SATD](https://github.com/alessandro-bergamo/Indentify-SATD)







<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=flat-square
[contributors-url]: https://github.com/othneildrew/Best-README-Template/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=flat-square
[forks-url]: https://github.com/othneildrew/Best-README-Template/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=flat-square
[stars-url]: https://github.com/othneildrew/Best-README-Template/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=flat-square
[issues-url]: https://github.com/othneildrew/Best-README-Template/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=flat-square
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=flat-square&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/othneildrew
[product-screenshot]: images/screenshot.png
