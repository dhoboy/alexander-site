# alexander-site

A web application that FIXME

## Getting Started

### Project Overview

* Architecture: [Web Application (multi-page)](https://en.wikipedia.org/wiki/Web_application)
* Language: [Clojure](https://clojure.org/)
* Domain-specific languages
  - CSS rendering: [Garden](https://github.com/noprompt/garden)
  - HTML rendering: [Hiccup](https://github.com/weavejester/hiccup)
* Other dependencies
  - System management: [Integrant](https://github.com/weavejester/integrant)
  - Web server: [Ring](https://github.com/ring-clojure/ring)
  - Router: [reitit](https://github.com/metosin/reitit/)
  - Localization: [Tongue](https://github.com/tonsky/tongue)
  - Authentication/authorization: [buddy-auth](https://github.com/funcool/buddy-auth)
  - Development-only
    - On-change task runner: [Lein-Auto](https://github.com/weavejester/lein-auto)
    - CSS compilation: [lein-garden](https://github.com/noprompt/lein-garden)
    - Development web server: [Lein-Ring](https://github.com/weavejester/lein-ring)
* Development tools
  - Linter: [clj-kondo](https://github.com/borkdude/clj-kondo)

#### Directory Structure
* [`/`](/../../): project config files
* [`.clj-kondo/`](.clj-kondo/): lint config and cache files (cache files are not tracked; see [`.gitignore`](.gitignore))
* [`dev/`](dev/): source files compiled only with the `dev` profile
  - [`user.clj`](dev/user.clj): symbols for use during development in the
[REPL](#starting-and-connecting-to-the-repl)
* [`resources/public/`](resources/public/): static files; url `/s/` routes here
  - Generated directories and files
    - `css/`: compiled CSS (`lein-garden`, can also be [compiled manually](#compiling-css-with-lein-garden))
* [`src/`](src/): application source files
  - [`alexander_site.clj`](src/alexander_site.clj): application entry point and system manager
  - [`alexander_site/`](src/alexander_site/): application modules and namespaces
    - [`app_ui/`](src/alexander_site/app_ui/): application screen template
      - [`ui_system_data.clj`](src/alexander_site/app_ui/ui_system_data.clj): merged routes, strings, and styles for all screens and components in the module
    - [`ui_system/`](src/alexander_site/ui_system/): ui system coordination and utility functions
    - [`router.clj`](src/alexander_site/router.clj): application router
    - [`strings.clj`](src/alexander_site/strings.clj): app-wide strings and translations
    - [`styles.clj`](src/alexander_site/styles.clj): app-wide styles, and a handler to provide a dynamically generated stylesheet
* [`test/`](test/): test source files
  - Only namespaces ending in `-test` (files `*_test.clj`) are compiled and sent to the test runner

### Editor/IDE

Use your preferred editor or IDE that supports Clojure/ClojureScript
development. See [Clojure tools](https://clojure.org/community/tools) for some
popular options.

### Environment Setup

1. Install [JDK 8 or later](https://openjdk.java.net/install/) (Java Development Kit)
2. Install [Leiningen](https://leiningen.org/#install) (Clojure project task & dependency management)
3. Install [clj-kondo](https://github.com/borkdude/clj-kondo/blob/master/doc/install.md) (linter)
4. Clone this repo and open a terminal in the `alexander-site` project root directory
5. Download project dependencies:
    ```sh
    lein deps
    ```
6. Setup [lint cache](https://github.com/borkdude/clj-kondo#project-setup):
    ```sh
    clj-kondo --lint "$(lein classpath)" --dependencies --parallel --copy-configs
    ```
7. Setup
[linting in your editor](https://github.com/borkdude/clj-kondo/blob/master/doc/editor-integration.md)

## Development

### Running the App with Hot Reload

Start a temporary local web server, build the app with the `dev` profile, and
serve the app with hot reload:

```sh
lein ring server 3000
```

This will automatically open and navigate your default browser to the
application main page.

### Starting and Connecting to the REPL

```sh
lein repl
```

Once the REPL starts, you may connect to it from your editor. Note that some
tools require the REPL to be started with a wrapper. For example, `vim-iced`
requires the REPL to be started via the following command:

```sh
iced repl
```

### Running Tests

Build the app with the `dev` profile and run tests:

```sh
lein test
```

To automatically run tests whenever a project file is updated, use the following
command:

```sh
lein auto test
```

### Compiling CSS with `lein-garden`

NOTE: for smoother development, the application uses a dynamically generated
stylesheet provided by the `handler` in
[`styles.clj`](src/alexander_site/styles.clj). Switc to the statically
generated spreadsheet by uncommenting the link element in
[`screen.clj`](src/alexander_site/app_ui/screen.clj).

Use Clojure and [Garden](https://github.com/noprompt/garden) to edit styles in
`.clj` files located in the [`src/`](src/) directory.

Manually compile CSS files:
```sh
lein garden once
```

The `resources/public/css/` directory is created, containing the compiled CSS
files.

#### Compiling CSS with Garden on change

Enable automatic compiling of CSS files when source `.clj` files are changed:
```sh
lein garden auto
```

## Production

Build the app with the `prod` profile:

```sh
lein uberjar
```

The `target/uberjar/` directory is created, containing the compiled JAR and
standalone JAR (uberjar) archive files.

## Copyright

Copyright © FIXME
