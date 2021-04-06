# Text-Editor
## Challenging project from JetBrains Academy
You can see description of task using this [link](https://hyperskill.org/projects/38).

# Solution
I decided to split graphical and logical parts of Text-Editor into two separates parts, called `View` and `Controller`

### [View](https://github.com/AlexeyShik/Text-Editor/blob/main/src/editor/View.java)
Interface with methods, that are required for implementing graphical part of application. For example, if user wants to find next occurence of pattern in text, GUI must be able to highlight corresponding occurence. (Method `highlightText` is required to do it.)

### [TextEditor](https://github.com/AlexeyShik/Text-Editor/blob/main/src/editor/TextEditor.java)
Class that implements `View`. It sets the appearance of application.

### [Controller](https://github.com/AlexeyShik/Text-Editor/blob/main/src/editor/Controller.java)
Interface with methods, that are required for implementing logical part of application. For example, if user wants to open file and clicks corresponding button, then application must load content of this file in `textArea` and let user to edit it. (Method `onOpen` is required to do it.)

### [TextEditorController](https://github.com/AlexeyShik/Text-Editor/blob/main/src/editor/TextEditorController.java)
Class that implements `Controller`. It sets logical behaviour of application.

## [Search package](https://github.com/AlexeyShik/Text-Editor/tree/main/src/editor/search)
Implements "Strategy" design pattern for searching pattern in text.

### [Searchable](https://github.com/AlexeyShik/Text-Editor/blob/main/src/editor/search/Searchable.java)
Common contract for all search strategies

### [AbstractSearcher](https://github.com/AlexeyShik/Text-Editor/blob/main/src/editor/search/AbstractSearcher.java)
Abstract base class for all searching strategies

### [EmptySearcher](https://github.com/AlexeyShik/Text-Editor/blob/main/src/editor/search/EmptySearcher.java)
Initial searcher that is used at application start, when no files were loaded to `textArea` and there is nowhere to search pattern

### [SimpleSearcher](https://github.com/AlexeyShik/Text-Editor/blob/main/src/editor/search/SimpleSearcher.java)
Represents search strategy without regular expressions

## [RegexSearcher](https://github.com/AlexeyShik/Text-Editor/blob/main/src/editor/search/RegexSearcher.java)
Represents search strategy with regular expressions
