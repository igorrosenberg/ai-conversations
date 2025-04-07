## General overview

Gamified UI to support spaced-repetition learning.

## Top-level: The requirements in one line

List of (question/answer) is loaded, and is randomly cycled through. First the question is shown, and upon user click, the answer is shown.

## High-level: Use-case and motivation, raison d'etre

On a high level, the software solution is a web-app, written in Next.js. The flow is:

- (Welcome page) User sees a blank screen with a spinner.
- A List of {List(question/answer)} is loaded from an https endpoint. It is a JSON file,  containing a list of objects. These objects have two fields, "url" and "name"
- (Welcome page) User sees is the list of names, wich replaces the spinner. 
- User selects a List by clicking on it. 
- (List page) User sees a blank screen with a spinner. Text: "Loading list..."
- The List of (question/answer) is loaded from the URL. It is a plain UTF-8 JSON file, containing a list of objects. These objects have two fields, "question" and "answer".  
- The list is sorted randomly
- Loop
	- the first (question/answer) is selected
	- (Question Page) The question part is shown on-screen
	- User clicks onscreen
	- (Answer page) The answer part is shown on-screen
	- User clicks onscreen
	- the first element of the list is removed from the list
- End of loop : return to initial screen (Welcome page)

The motivation for this solution is to allow the User to self-test his/her knowledge, by confirming the correctness of the guess.

No admin UI is expected to handle the update of the lists, JSON editing skills and a simple http server is sufficient for a first version.

## Mid-level: Process and work mechanisms

The system is a Next.JS app with a very limited number of features

#### List of pages

(Welcome page) presents a spinner (Text: "Loading app...") and loads asynchronously the List of {List(question/answer)}. It is a plain UTF-8 JSON file, referenced as $all_lists, containing a list of objects. These objects have two fields, "url" and "name". "url" value is a URL. "name" value is a plain string. [interaction: click on a List => store $one_list, show the (List page)]

(List page) presents a spinner ( Text: "Loading list...") and loads asynchronously the List(question/answer). It is a plain UTF-8 JSON file, referenced as $one_list, containing a list of objects. These objects have two fields, "question" and "answer". For both, values are plain strings, either text or an URL.

(Question Page) The question part is shown on-screen
		- [interaction: click anywhere on the page => show the (Answer Page)]

(Answer Page) The answer part is shown on-screen
		- [interaction: click anywhere on the page => show the (Question page)]

#### Process

Here is a blow-by-blow description for all steps, with their respective Inputs,
Main operations, Side Effects, and Outputs:

Load asynchronously: use the standard Next.js mechanism to fetch an external resource

The (List Page) shows rows of ($name, $url, $y). $y is the initial size of the list. $name and $url are read from the $all_lists data. A CSS rule makes sure $url is not visible.

(Question Page) [input=($question/$answer)=the first element of $one_list] The question part is shown on-screen
		- if $question is a URL, $question is shown as an image
		- else, $question is loaded as text 
		- [interaction: click anywhere on the page shows the (Answer Page)]

(Answer Page) [input=($question/$answer)=the first element of $one_list] The answer part is shown on-screen
		- if $answer is a URL, answer is shown as an image
		- else, $answer is loaded as text 
		- [interaction: click anywhere on the page shows the (Question page)]

Next Question: the first element of the list is removed from the list. If the list is not empty, show (Question Page). If the list is empty, show the (Welcome page).

Progress: in the top right corner of the (Answer Page) and (Question Page), show "$x/$y" where $x is the count of questions already seen, starting at 1, and $y is the initial size of the list. $x is incremented after each (Answer Page), whereas $y isn't.


## Mid-level: General architecture, tech stack, technological contraints

Users must be able to install and set up this software solution application quickly and easily on typical unixoid
computer systems like macOS, GNU/Linux, or a BSD variant, without being disproportionately bothered with additional
dependencies that would need to be in place before the software can work.

Therefore, the general constraints that inform the architecture and tech stack look like this:

- The application can be installed by downloading its program files into a single local folder (e.g. through git clone),
  followed by a manageable amount of setup procedure. The resulting installation of the application is then more or
  less "self-contained".
- The application can be run from any widespread command-line shell (e.g. sh, bash, zsh) by starting a single central
  command from within the installation folder.

The following prerequisites are assumed to be fulfilled for the software to be able to do its jobs:

- An http server is available and can, network-wise, be reached and read by means of HTTP requests that originate from the machine that hosts the software application.
- Any information that is required to connect to any services is provided through
  a central, locally available configuration file whose path is provided when launching the application.
- the locally available configuration file has the following structure:

    ALL_LISTS_URL=""


Some further assumptions:

- The application must not assume that any state from previous runs is available
- The application is a web application
- If any step of the process fails, the entire process should fail immediately

The tech stack for this application is defined as follows:

- It is a software application written in Javascript using Next.js, and provided in source-code form
- Setup and dependencies are managed via the standard approach
- A bash shell script is provided which allows the user to start the application in a straightforward manner, e.g.
  ./spaced-repetition <path-to-config-file>


The architecture is defined as follows:
- Pages as mentioned in the section above "List of pages"
- an http-client wrapper

## Performance considerations

Spinners will notify the user when a download process starts.
A spinner always comes with an option "cancel" which returns to the previous page. 

To avoid unnecessary garbage-colection, the variables are all globals. 

## Low-level: excruciating detail 

The resulting code must be clean, well documented, with concise and comprehensible naming.


## Code quality requirements and practices

The codebase must adhere to strict quality standards to ensure 
maintainability, readability, and correctness. 

The following quality assurance practices and tools must be employed:

### Development workflow and quality checks

- All code must pass automated coherence checks before being committed to the repository. This concerns linting, file structure, file naming, etc.
- A pre-push hook system must be in place to automatically check code before pushing
- Continuous Integration (CI) must run quality checks on multiple browsers
- A single comprehensive file must be provided to streamline development tasks, according to Next.js standard


### Code formatting and style

- All code must respect accepted practices of the language/framework

### Static analysis and type safety

- state-of-the art code analysis must be included. 

### Testing requirements

- Unit tests must cover any non-trivial algorithms
- Integrqtion Tests qre not needed
- Functional Tests must verify Flow behavior


## Discarded as not relevant

- An AWS S3 bucket stores the list files
- Sharing functionality in a library or re-usable files or functions is not an objective, respecting the DRY principle is not needed 
