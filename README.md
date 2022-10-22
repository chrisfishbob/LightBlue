# LightBlue

![alt text](https://user-images.githubusercontent.com/56414801/197311485-cc12c815-b695-46b7-87bf-4c9fe11017b9.png)


## What is this?
LightBlue is an independent chess engine with a built-in GUI. This project uses Java for all aspects of the engine, utilizing the Processing library for
the graphical user interface.


## How to run this?
Run LightBlue.jar!
(Note: This engine uses Processing for its UI and sometimes this causes issues with different Java and Processing versions. I would recommend recompiling the project if that is the case. For my next engine, we are scrapping the UI and are going for a UCI compatible engine instead. The new engine (name pending) is still a work in progress but should be available here on GitHub publically probably by Fall 2023.)


## How was this developed
This project will be completed in these stages:

<b>Stage 1:</b> Initial setup- During this stage, the backbones of this project will be set up. Most of the graphical aspects will be completed here. Some of the features
         that will be completed in this stage includes but are not limited to: "board graphical representation, piece graphical representation, board array representation 
         in code. Piece classes with inheritance, mouse and keybaord behavior.
         
<b>Stage 2:</b> Generating moves- This stage will largely be focused on generating all the legal moves that are possible by all the pieces on the board. In addition, special rules
         such as castling and en passant will be implemented here. This will likely be the longest and most difficult stage, as any slight imperfections made here will ruin render the searching algorithm useless.
        
<b>Stage 3:</b> Searching algorithm- At this point, the searching algorithm will be developed using min-max with alpha-beta pruning. The evaluation of the position will be focused purely
         on material advantage only at this stage
         


(This project is being replaced and will receive no further commits. There was more stuff planned for LightBlue, but I want to move to a compiled language and go for UCI compatibility instead! Getting the UI to to compile and run across different machines with Processing is a nightmare (at least on the version that I used)


There will be another version of LightBlue written in Go coming in the near future!

