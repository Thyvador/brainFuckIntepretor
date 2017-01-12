# PROJET BRAINF*CK

## Team Information

  * Team name: Foobar Team
  * Members:
    *  Alexandre Hiltcher : Thyvador
    *  Jeremy Junac : Taken0711
    *  François Melkonian : arsebac
    *  David Lang : fitzzzz
    
    
## Automatisation de la construction du logiciel
 La construction de l'executable se fait a l'aide de la commande
  ```shell
 mvn install
 ```
 
 ## Syntaxe d'un code Brainf*ck
  ### Syntaxe d'une macro
   ```!macro(arg1, arg2) groupe_instrucktion arg groupe_instrucktion arg  ...7
   ```
   <p>Une macro est définie sur 1 ligne, chaque d\'instruction peut être suivie par un argument. Le groupe d\'instruction sera répeter autant de fois que l\'argument l\'indique, s\'il n\'y a pas d'argument le groupe d\'instruction sera exécuté une seule fois.</p>
   <p>Un groupe d\'instruction peut être une instruction longue, plusiers instruction (ne contenant pas d\'espace) ou une macrod définie précédement.</p>
  
  ### Syntaxe d'une procedure
   ```!procedure(arg1, arg2){
      instructions
      instructions
   !end
   ```
   <p>Une procedure peut être définie avec ou sans arguments. </p>
 
  ### Syntaxe d'une fonction
   ```!fonction(arg1, arg2){
      instructions
      instructions
   !end
   ```
   <p>Une fonction peut être définie avec ou sans arguments. </p>
