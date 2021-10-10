# LightBlue

LightBlue is an independent chess engine with a built-in GUI. This project uses Java for all aspects of the engine, utilizing the Processing library for
the graphical user interface. 

This project will be completed in five stages:

<b>Stage 1 (completed):</b> Initial setup- During this stage, the backbones of this project will be set up. Most of the graphical aspects will be completed here. Some of the features
         that will be completed in this stage includes but are not limited to: "board graphical representation, piece graphical representation, board array representation 
         in code. Piece classes with inheritance, mouse and keybaord behavior.
         
<b>Stage 2 (Completed):</b> Generating moves- This stage will largely be focused on generating all the legal moves that are possible by all the pieces on the board. In addition, special rules
         such as castling and en passant will be implemented here. This will likely be the longest and most difficult stage, as any slight imperfections made here will ruin render
         the searching algorithm useless.
        
<b>Stage 3 (In Progress):</b> Searching algorithm- At this point, the searching algorithm will be developedsing min-max with alpha-beta pruning. The evaluation of the position will be focused purely
         on material advantage only at this stage
         
<b>Stage 4 (pending):</b> Improving evaluation: This stage will see the improvement of the evaluation function. Rules and checks will be put in place such that the engine has a better grasp 
         of concepts such as activity, king safety and pawn structure.
         
<b>Stage 5 (ending):</b> End game and opening: A data base will be used from grandmaster games so that the engine will play book openings up until a certain point. The end game portion seeks to 
         modify and improve upon the existing evaluation function to better execute endgame strategies.
         
         
