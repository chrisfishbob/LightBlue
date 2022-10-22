# LightBlue

![alt text](https://user-images.githubusercontent.com/56414801/197311485-cc12c815-b695-46b7-87bf-4c9fe11017b9.png)


## What is this?
LightBlue is an independent chess engine with a built-in GUI. This project uses Java for all aspects of the engine, utilizing the Processing library for
the graphical user interface. 


## How to run this?
Run LightBlue.jar!
(Note: This engine uses Processing for its UI and sometimes this causes issues in different machines. I would recommend recompiling the project if that is the case. For my next engine, we will scrap the UI and go for a UCI compatible engine instead.)


## How was this developed
This project will be completed in these stages:

<b>Stage 1:</b> Initial setup- During this stage, the backbones of this project will be set up. Most of the graphical aspects will be completed here. Some of the features
         that will be completed in this stage includes but are not limited to: "board graphical representation, piece graphical representation, board array representation 
         in code. Piece classes with inheritance, mouse and keybaord behavior.
         
<b>Stage 2:</b> Generating moves- This stage will largely be focused on generating all the legal moves that are possible by all the pieces on the board. In addition, special rules
         such as castling and en passant will be implemented here. This will likely be the longest and most difficult stage, as any slight imperfections made here will ruin render
         the searching algorithm useless.
        
<b>Stage 3:</b> Searching algorithm- At this point, the searching algorithm will be developedsing min-max with alpha-beta pruning. The evaluation of the position will be focused purely
         on material advantage only at this stage
         


(This project will receive no commits. There was more stuff planned for LightBlue, but I want to move to a faster language and go for UCI compatibility instead! Getting the UI to render consistently across different machines with Processing is a nightmare (at least on the version that I used),


