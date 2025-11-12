# Simon Dice

Esta es una versión del juego Simon Dice, para Android, el juego consiste en que tenemos una secuencia, de colores que nosotros tenemos que repetir

``` mermaid
---
stateDiagram
    [*] --> Inicio
    Inicio --> Generando : Creando secuencia
    Generando --> Jugando : Iniciar adivinacion secuencia
    Jugando --> Finalizando : Adivinaste o no la secuencia

```
Como se puede ver aqui el juego es bastante sencillo en mi caso puse un estado finalizando y no uno perdiendo
este estado es bastante general ya que por lo general no se suele relacionar con perder directamente pero bueno, estoy pensando en si deberia añadir un perdiendo


