# cping

Simple project for visualizing ping results over time. Let's you "see" ping. Get it?

## Requirements

You must have [leiningen](http://leiningen.org/) installed.

## Using

Compile and run the script like so:

    ./run.sh ip_address

Also, you can symlink to the script and use it that way:

    ln -s $(pwd)/run.sh ~/bin/cping

The first time you run the script, it will download any dependencies and build
the program. You can remove the build to force a rebuild if you like:

    lein clean

## License

Copyright © 2014 Christoph Neumann

Distributed under the Apache Public License v2.0.
