# Simon Dice

Esta es una versión del juego Simon Dice, para Android, el juego consiste en que tenemos una secuencia, de colores que nosotros tenemos que repetir

``` mermaid
---
config:
  theme: redux-dark
  look: neo
---
stateDiagram
    [*] --> Inicio
    Inicio --> Generando : Creando secuencia
    Generando --> Jugando : Iniciar adivinacion secuencia
    Jugando --> Finalizando : Adivinaste o no la secuencia

```

