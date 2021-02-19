<br />
<p align="center">
  
  <h3 align="center">
    <img src="https://i.imgur.com/8Vd8Ynp_d.webp?maxwidth=500&fidelity=grand"></img>
   </h3>
  
  <h3 align="center">Identify SATD</h3>

  <p align="center">
    Identify SATD è un plugin per IntelliJ utile all'identificazione dei "Self Admitted Technical Debt"
    <br />
    <a href="https://github.com/alessandro-bergamo/Identify-SATD/tree/main/Identify-SATD/src"><strong>Esplora il codice »</strong></a>
    <br />
    <br />
    ·
    <a href="https://github.com/alessandro-bergamo/Identify-SATD/issues">Segnala Bug</a>
    ·
    <a href="https://github.com/alessandro-bergamo/Identify-SATD/issues">Richiedi Feature</a>
  </p>


<!-- TABLE OF CONTENTS -->
## Guida al README

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Instructions](#instructions)
* [Decisions](#decisions)
* [Contact](#contact)
* [Credits](#credits)


<!-- ABOUT THE PROJECT -->
## About The Project

<h3>Identify SATD</h3>

Un "Self Admitted Technical Debt" è un modo per segnalare di aver pubblicato del codice incompleto, che richiede un rifacimento, che produce errori o che è stato realizzato per permettere all'intero sistema di poter funzionare, ma con la premessa che saranno necessarie future modifiche.

Questo plugin mira ad identificarli e mostrarli attraverso l'analisi testuale del <i>commit message</i>.


### Built With
Il plugin è realizzato interamente in:
* [JetBrains IntelliJ - v.2020.2.3](https://www.jetbrains.com/idea/)
* [Java - v.1.8.0](https://www.java.com/)
* [Weka - v.3.8.4](https://www.cs.waikato.ac.nz/ml/weka/)
* [JGit - v.5.9.0](https://www.eclipse.org/jgit/)
* [SLF4J - v.1.7.30](http://www.slf4j.org/)


<!-- Instructions -->
## Instructions

Il plugin si sofferma su pochi ed incisivi passi, tra cui:

1. Clone della repository github 
2. Recupero dei commit
3. Analisi testuale dei commit message
4. Identificazione delle possibili "keywords" potenzialmente rappresentanti dei Self-Admitted Technical Debt
5. Segnalazione del Commit ID, user, date e message identificati al passo precedente


<!-- Decisions -->
## Decisions

* 22/10/2020 

Durante le fasi primordiali di sviluppo si è deciso di strutturare l'intero plugin con due moduli ben distinti sulla base del modello [Onion Architecture](https://www.codeguru.com/csharp/csharp/cs_misc/designtechniques/understanding-onion-architecture.html#:~:text=Onion%20Architecture%20is%20based%20on,on%20the%20actual%20domain%20models.)

Un primo modulo presenta il core esecutivo del plugin, ovvero tutto il codice riguardante l'identificazione dei SATB.
Il secondo modulo presenta la parte di interazione tra il core del plugin con l'IDE stesso, quindi va a gestire tutti gli input e output.

Questa scelta è stata presa per rendere il core del plugin ben separato dall'IDE in cui è stato sviluppato il plugin, così da renderlo utile e soprattutto riutilizzabile con altri IDE al di fuori di IntelliJ, mantenendo la parte di logica del software separata dalla parte di input/output.

* 17/11/2020

E' stato deciso di implementare il plugin con una classe temporanea "DummySATDDetector" che va a sostituire, per l'appunto, temporaneamente il classificatore finale che verrà utilizzato. 

Il tutto è stato progettato ed implementato in modo da favorire la futura aggiunta del classificatore finale rispettando le dipendenze tra le varie classi e rendendo il tutto più portabile e manutenibile soprattutto garantendo l'utilizzo anche in seguito a future implementazioni del classificatore.   
 
* 24/11/2020
 
E' stato deciso di implementare una interfaccia grafica di base che desse l'idea di mostrare i risultati prodotti dal classificatore. 

L'interfaccia è realizzata attraverso una lista di "Commit" mostrati a video tramite una Java Graphic User Interface. Successivamente, è stato deciso di aggiungere funzionalità a questa GUI, attraverso l'utilizzo di shortcuts utili alla selezione rapida dell'elemento dalla lista.
 

<!-- CONTACT -->
## Contact

Alessandro Bergamo - [@linkedin](https://www.linkedin.com/in/alessandro-bergamo-4a21b11ba/) - a.bergamo2@studenti.unisa.it

Project Link: [https://github.com/alessandro-bergamo/Identify-SATD](https://github.com/alessandro-bergamo/Identify-SATD)

