# Cluster program
Made by: Ina Vangen

# Introduction:
The program shows a graphical representation on how different elements are related to each other and takes a .txt file as an input source (examples in repo).

The user gets to choose in which way the clustering process should proceed as well as which sort of diagram should be shown (cartesian or a dendrogram).


# Installation:
1. Compile using: javac *.java
2. Run the program with: java Clustering
3. Select the input text file (milk.txt, flower.txt etc)
4. Choose the clustering method and distance measure.
5. Choose which diagram prefered.
6. Use any key to iterate through every step of the clustering process


# Important:
- Make sure libUI and ui are found in the same directory.
- If the file choosen does not exsist or contains data in the wrong format, no plotting will be shown. 
- Program might crash when double tapping.

# Imrpovements:
- Clean up code (it's quite messy).
- Make code more failsafe.