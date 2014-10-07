libgdx_game
===========

Game project that uses LibGdx to build for desktops, Android and web simultaneously.

It is in a very early stage, and is on hold now. Still, it can be an useful source of LibGdx drawing basics.

As you might have noticed, drawing animated sprites can be a little tricky with LibGdx at a first glance.
You can see what I mean in this thread:
https://stackoverflow.com/questions/16059578/libgdx-is-there-an-actor-that-is-animated

Anyway, the solution I used is not to extend Image but the Actor class instead, and utilize the Stage/Actor mechanism.
It puts on a little more load, but is way easier to use. 
You can see the AnimActior.java implementation (and how it is used in MainGame) for details.
