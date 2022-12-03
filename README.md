# File-encryption
This is an encryption tool that uses the AES256 cipher to encrypt your Files

## How to use
You can use the tool by selecting  a file then pressing the encryption button. If you want to decrypt the file just press "decrypt" however, if you want to decrypt it at a later date or want to send to another person, press the "save config" button. This should create a config with all the needed parameters needed to decrypt the file this file should be located in your execution directory (of the jar file).


## IMPORTANT !!
Currently, this program is only tested on linux and some code for windows is not implemented yet. 


## Roadmap

- [ ] add more encryption methods
  - [ ]  make Plugin support
- [X] allow different character sets
- [X] add support for more file types
    - [X] jpg
    - [X] png
    - [x] text
    - [X] webp
    - [X] gif
    - [X] mp4
    - [X] webm
    - [X] zip
    - [X] mov
    - [X] mkv
- [ ] add threading for multiple files
- [ ] decrypt multiple files
- [X] add native file chooser so that it looks better
  - upgrade the look of the file chooser
- [X] switch Gui frontend to https://github.com/SpaiR/imgui-java
  - [X] finish encryption window
  - [X] finish decryption window
  - [X] finish settings menu
    - [ ] finish Plugin window
### dependencies

- https://github.com/SpaiR/imgui-java
- https://github.com/johnrengelman/shadow (for build process)