# giro

> QR code powered, User-Agent based link redirection

This is a short-url like service that redirects to difference destinations
based on the device of the user as guestimatted from the UserAgent header.

The use case is to create a single QR Code that can be printed/published, but
will redirect to a different URL (such as the iOS App Store or the Google Play
Store) based on the device of the visitor.

**The current state of this project is a MVP and works, but is not polished.
Contact me if you think this would be useful to you.**

[![](https://thumbs.gfycat.com/ImpressiveChillyIndochinesetiger-size_restricted.gif)](https://github.com/Ramblurr/giro/raw/master/giro.webm)

### Related Services

Other services that perform this same service are available (see the list
below). But there is a security liability in publishing a QR Code/short
link that you do not actually control. Better to self host it.

If you're really a company and want to do this, then this could easily be
implemented in nginx/apache directly, or as a short PHP script. Consider that
before an over engineered solution like this.

This project is similiar in functionality to the following proprietary
services:

* [onelink.to](http://onelink.to)
* [appURL](https://manage.appurl.io/)
* [OmniQR](http://omniqrcode.com/)

## Develop

I always love an excuse to play with Clojure.

Project was initially generated using Luminus version "2.9.12.08"

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein run 

## License

Copyright © 2018 Casey Link <unnamedrambler@gmail.com>

Novel bits (i.e., non-luminus generated) are licensed under AGPL v3.0
