# Trabajo Practico Programacion Concurrente
---
## Integrantes:

- [**Gerard Brian**](https://github.com/brian1062)
- [**Rodríguez Emanuel**](https://github.com/Ema-Rodriguez)
- [**Viotti Franco**](https://github.com/franco-viotti)

---
[Enunciado Trabajo Practico](https://github.com/brian1062/tp_final_programacion_concurrente/blob/master/Enunciado_TP_Final_Concurrente_2024.pdf)

---
## Introducción

El presente informe detalla el análisis, modelado, y simulación de una red de Petri aplicada a un sistema de agencia de viajes, utilizando herramientas de análisis como Petrinator y implementaciones en Java para la simulación y control de concurrencia. Este trabajo se enmarca dentro del contexto de la asignatura Programación Concurrente y tiene como objetivo principal estudiar y demostrar el comportamiento de sistemas concurrentes mediante el uso de redes de Petri, un modelo gráfico y matemático ampliamente utilizado en la modelización de sistemas distribuidos.

---
## Descripción de la Red de Petri
![Red de petri](/images/red_de_petri.png)
La red de Petri utilizada en este trabajo modela el flujo de clientes y la gestión de reservas en una agencia de viajes. Cada lugar y transición dentro de la red representa diferentes estados y eventos en el sistema. A continuación, se describen las principales plazas y transiciones:

* **P0 (Idle)**: Representa el buffer de entrada de clientes a la agencia.
* **P1, P4, P6, P7, P10 (Recursos Compartidos)**: Plazas que modelan recursos compartidos en el sistema, como agentes de reservas y áreas de gestión.
* **P2 (Ingreso a la Agencia)**: Representa el ingreso de un cliente a la agencia de viajes.
* **P3 (Sala de Espera)**: Lugar donde los clientes esperan antes de ser atendidos por un agente.
* **P5, P8 (Gestión de Reservas)**: Estados del sistema en los cuales se gestionan las reservas de los clientes.
* **P9 (Espera para Aprobación/Rechazo de Reservas)**: Modela la espera de los clientes mientras su reserva es procesada por un agente.
* **P11 (Confirmación de Reserva)**: Plazas que representan la confirmación de una reserva por parte del agente.
* **P12 (Cancelación de Reserva)**: Lugar donde se modela la cancelación de una reserva por parte del cliente o del agente.
* **P13 (Pago de la Reserva)**: Representa el momento en que el cliente realiza el pago de la reserva confirmada.
* **P14 (Salida del Cliente)**: Instancia previa a que el cliente se retire de la agencia.

La estructura de esta red de Petri permite capturar de manera efectiva las interacciones y dependencias entre los diferentes estados y procesos del sistema de la agencia de viajes.

## Análisis de propiedades de la red

Se hizo uso de la herramienta “Petrinator” para análisis de la red la cual nos dio como resultado las siguientes propiedades:

![Propiedades](/images/petri_property.png)

La red de Petri es **simple y extendida**, lo que significa que las transiciones reciben entradas de una sola plaza, o bien, en aquellos casos donde reciben más de una, el conjunto de una de estas últimas está contenido o es igual al de las otras. Esta característica asegura una estructura regular en la red, lo que facilita su análisis y garantiza la coherencia en el flujo de tokens a través del sistema.
### Limitación
La red de Petri es **limitada**, lo que significa que la cantidad de tokens en cada lugar nunca excederá un valor máximo predefinido. Esta propiedad es fundamental para evitar un crecimiento ilimitado en cualquier parte del sistema, lo que podría llevar a estados de desbordamiento o comportamiento incontrolado. La limitación asegura que el sistema se mantenga dentro de los parámetros operativos previstos, previniendo la acumulación excesiva de tokens en cualquier lugar.
### Seguridad
Sin embargo, a pesar de ser limitada, la red **no es segura**. Esto significa que, en algunos estados alcanzables desde la marca inicial, uno o más lugares pueden contener más de un token. Esta condición podría representar la ocurrencia simultánea de eventos que deberían ser mutuamente excluyentes, lo que podría tener implicaciones en la concurrencia y sincronización de eventos dentro del sistema. En el contexto de la agencia de viajes, esto puede reflejar situaciones en las que múltiples clientes o reservas están siendo gestionados simultáneamente, lo cual es aceptable pero requiere supervisión para evitar sobrecargas.
### Ausencia de Deadlock
La red de Petri **no presenta deadlock**, lo que es una propiedad crucial en sistemas concurrentes. Un deadlock es una situación en la que ninguna transición del sistema puede ser disparada, provocando que el sistema se quede atascado en un estado inactivo. La ausencia de deadlocks asegura que desde cualquier estado alcanzable siempre existe al menos una transición que puede ser disparada, garantizando que el sistema nunca se quedará atrapado sin posibilidad de avanzar. Esta propiedad es esencial para mantener la continuidad operativa del sistema, especialmente en un entorno de agencia de viajes, donde la paralización de procesos podría tener consecuencias significativas.

## Invariantes de la Red.

El análisis de invariantes es crucial para comprender la conservación y repetitividad dentro de la red de Petri. Para llevar a cabo este análisis, se ha utilizado la matriz de incidencia, que juega un papel fundamental en la identificación tanto de los invariantes de plaza como de los invariantes de transición.

## Matriz de Incidencia
La matriz de incidencia de la red de Petri es una representación matemática que describe cómo las transiciones afectan los lugares en la red. Esta matriz se define como la diferencia entre la matriz de incidencia de salida $I^+$ y la matriz de incidencia de entrada $I^-$.
$$
C = I^+ - I^-
$$
* $I^+=$ Indica cómo las transiciones añaden tokens a los lugares.
* $I^-=$ Indica cómo las transiciones remueven tokens de los lugares.
