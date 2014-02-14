TurtleGame
==========

Fledgling beginnings of a turtle-like (as in LOGO, not mutant ninja) game for children - written in Java.


Screenshots
===========

![Game: None](TurtleGame-1.png)
![Game: One Barrier](TurtleGame-2.png)
![Game: Three Barriers](TurtleGame-3.png)


Commands
========

* forward <steps>
* left <steps>
* right <steps>

You can abbreviate these commands with single letters, e.g. "f 5" instead of "forward 5".  They may be uppercase or lowercase - it doesn't matter.

Example
=======

    forward 3
    left 2
    forward 5
    right 2

The stage area is approx 100 steps x 100 steps, and a full circle is 16 rotation steps (so 4 rotation steps = 90 degrees).


Games
=====
* None - practice moving the turtle around.
* Simple - get the turtle into the yellow gate.
* One Barrier - get the turtle into the yellow gate without touching the red barrier.
* Three Barriers - weave the turtle in and out of the barriers to get to the gate.

Why?
====

A made-up computer game featured in a bedtime story that I invented for my 5-year-old daughter - this is the implementation of that idea.