# ***!!ATTENZIONE: Questo file definisce un codice di condotta a cui cercherò di attenermi // Team BernersLee // IS // Agile Development***
>Leggendolo è come se accettassi di ***giocare*** con queste ***"Le regole del gioco"*** o è come se ***accettassi*** di lavorare con questo ***"Contratto di Lavoro"*** .
## *Partecipazione al team*
![Img_team](img/team.jpg)
- La mia priorità assoluta è correggere i **problemi** nei build del mio team. 
- La partecipazione alle **revisioni** del codice del mio team è la mia seconda priorità. 
- Migliorare la **velocità del mio team** è più importante che migliorare la mia **velocità individuale**. 
- Non arriverò in **ritardo** alle riunioni del team. 
- Chiederò ***aiuto** quando ne avrò bisogno. 
- Fornirò aiuto quando me lo **chiederanno**. 
- Sarò sempre **onesto**. 
- Se non **ritengo possibile** realizzare qualcosa, lo dirò **senza esitazione**.
- Se **non sono sicuro** di quello che sto per fare, chiederò **consiglio o aiuto** ai miei compagni del team.
- Sarò convinto e cercherò di dare sempre il **massimo**.
- Sarò **puntuale e serio**. 
- Parteciperò agli incontri organizzati dal team, per **discutere**, **allinearmi** e **informare**. (Definiti settimanalmente o ogni 3 giorni via **WhatsApp** o **Github**) 
## *Peer reviews*
![Img_rev](img/recensione.jpg)
- Sarò professionale e diplomatico quando fornirò commenti di revisione. 
- Sarò aperto a ricevere feedback quando altri revisionano il mio lavoro. 
- Quando troverò errori, bug, ambiguità nel lavoro dei miei compagni riporterò (durante la discussione // pull request) tutti i riferimenti alla riga del documento o  del codice, spiegando come si potrebbe sistemare o chiarire. Riportando anche il motivo.
## *Scrittura del codice* 
![Img_code](img/code.jpg)
- Mi esprimerò **attraverso** il codice. 
- Aggiungerò **commenti** al codice solo se non riuscirò ad esprimermi attraverso quest'ultimo. 
- Scriverò codice che **aderisce alle convenzioni** di formattazione e stile del mio team. 
- Non invierò codice in produzione se non avrà passato i **test Github Actions**.
- Non invierò codice che provochi la **generazione** di un **avviso** da parte di uno strumento.
- Cercherò di scrivere codice rispettando il paradigma **OO ([Object Oriented](https://medium.com/geekculture/your-ultimate-guide-to-object-oriented-programming-conceptually-4e22d7cdb4f5))**, scrivendo classi quanto più complete e autoconsistenti.
## *Best practices & Flusso di lavoro*
![Img_flow](img/githubflow_new.png)
- Leggerò e studierò le dispense **fornite dal professore** (es. Cheat sheet della CLI || Slide || Scrum Manual/Links || Cheat sheet sui comandi GIT)
- Su **ogni dispositivo** che userò per lo sviluppo del progetto avrò una copia della repository remota e mi preoccuperò di **aggiornarla** ogni volta che avrò intenzione di prendere in carico una issue (ovviamente effettuerò tutte le operazioni per **l'autenticazione** alla repo remota).
>|> **git clone [[LinkRepo](https://github.com/softeng2223-inf-uniba/progetto2223-bernerslee.git)]**
- Userò il modello di **branching leggero** per lo sviluppo del software ([***Github Flow***](https://docs.github.com/en/get-started/quickstart/github-flow)).
>- Aggiornerò la working directory all’inizio di una sessione di lavoro o dopo aver preso in carico una ***issue***.
>>|> **git pull**
>- Creerò un branch sulla mia repository locale e lavorerò su quello per risolvere la issue (se necessario). Il nome del branch che andrò a creare avrà un nome esplicativo in merito alla issue che voglio risolvere.
>>|> **git branch [nomeBranchDaCrearePerRisolvereIssue]**
>>|> **git checkout [nomeBranchCreato]**
>- Dopo aver lavorato in locale e aver testato le funzionalità creerò un commit con messaggio riassuntivo e preciso su quanto è stato fatto. Utilizzerò **sostantivi** invece che verbi per indicare le operazioni fatte e cercherò di indicare sempre i percorsi. 
>>|>**git add .**<br>
>>|>**git commit -m "Creazione del file ./docs/CODE_OF_CONDUCT.md"**
>- Aggiornerò il repository remoto con i cambiamenti locali alla fine di
una sessione di lavoro o risoluzione di una issue.
>>|> **git push origin [nomeBranchCreato]**
>- Aprirò una pull request (se credo di aver consluso la risoluzione di una issue) facendo attenzione ai branch su cui applicare il merge.
>>|> **Home GitHub Repo -> Tab Pull requests -> New pull requests -> Base: Main || Compare: [branchDaMergiare] -> Create pull request**
>- Riporterò sulla pull request le parole chiave che [linkano](https://docs.github.com/en/issues/tracking-your-work-with-issues/linking-a-pull-request-to-an-issue) quest'ultima alla issue, il **#NumeroIssue** e specificherò le modifiche fatte (magari descrivendo anche il perchè di certe scelte). 
>>|> **es.** **Fixes #numero_issue**: indica che la pull request sistema completamente la issue specificata.<br>
>>|> **Resolves #numero_issue**: indica che la pull request risolve completamente la issue specificata.<br>
>>|> **Closes #numero_issue**: indica che la pull request chiude la issue specificata.<br>
>Riportando più informazioni possibili al fine di evitare ambiguità e incomprensioni. Imposterò sulla pull request:<br>
>|> **Milestone di riferimeto || ProjectBoard di riferimento || Assignees (Chi prende in carico la issue prende in carico anche la pull request creata) || Reviewers (Tutti i membri del team)**
>- Dopo aver ricevuto la conferma da almeno due membri del team effettuerò il merge e verificherò l'esito delle Actions. Se tutto è andato a buon fine cancellerò sulla **repo remota** il **branch** su cui ho lavorato e cancellerò anche il **branch** dalla **repo locale**. Altrimenti cercherò con l'**aiuto** dei miei compagni di sistemare prima di effettuare il merge e chiudere la pull request.
- Non modificherò la **storia** pubblica delle versioni.
- Rispetterò il **workflow adottato**.
- Non pusherò sulla **repository remota** file inutili o file costruiti dinamicamente dagli ambienti di sviluppo. (Nel caso **per evitare** ciò userò [./.gitignore](https://www.freecodecamp.org/italian/news/cosa-e-gitignore-e-come-aggiungerlo-al-tuo-repo/))
- Ad ogni start di uno **Sprint**, annunciato dal professore mi coordinerò con i miei compagni per creare le **issues** e posizionarle nella **project board** (tramite **WhatsApp** o **Github**). 
- Sposterò le issues nella project board a seconda dello stato **reale** in cui possono essere posizionate.
> |> **Todo** (L'implementazione della issue non è cominciata)<br>
> |> **In Progress** (L'implementazione della issue è in corso)<br>
> |> **Review** (La issue stata risolta ed ho aperto la pull request in attesa di conferma da parte dei miei compagni)<br>
> |> **Ready** (La issue deve essere approvata dallo Scrum Master (**Prof**))
- Non prenderò **iniziative strane** senza il **consenso** dei miei compagni.
> **es.** Scrivere sul canale Teams (#Consegne) che lo Sprint X è stato completato quando in realtà non lo è.
---
 -   ***MI DIVERTIRO' ;)***
---