[![License](https://img.shields.io/github/license/LordAkkarin/NBT.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.txt)
[![Latest Tag](https://img.shields.io/github/tag/LordAkkarin/NBT.svg?style=flat-square&label=Latest Tag)](https://github.com/LordAkkarin/NBT/tags)
[![Latest Release](https://img.shields.io/github/release/LordAkkarin/NBT.svg?style=flat-square&label=Latest Release)](https://github.com/LordAkkarin/NBT/releases)

NBT
===

A modern NBT library for Java featuring both an event based and a tree based API.

Table of Contents
-----------------
* [Contacts](#contacts)
* [License](#license)
* [Usage](#usage)
* [Downloads](#downloads)
* [Issues](#issues)
* [Building](#building)
* [Contributing](#contributing)

Contacts
--------

* [IRC #Akkarin on EsperNet](http://webchat.esper.net/?channels=Akkarin)
* [GitHub](https://github.com/LordAkkarin/NBT)

License
-------

Copyright (C) 2016 Johannes "Akkarin" Donath and other copyright owners as documented in the project's IP log.
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.txt)

Usage
-----

```java
TagVisitor visitor = new MyVisitor();
ValidationVisitor validationVisitor = new ValidationVisitor(visitor);

TagReader reader = new TagReader(inputStream);
reader.accept(validationVisitor);
```

```java
TreeVisitor visitor = new TreeVisitor();
ValidationVisitor validationVisitor = new ValidationVisitor(visitor);

TagReader reader = new TagReader(inputStream);
reader.accept(validationVisitor);

CompoundTag tag = reader.getRoot();

System.out.println(tag.getString("test"));
tag.setString("test", "foobar");

TagWriter writer = new TagWriter();
tag.accept(writer);
writer.write(outputStream);
```

Downloads
---------

Released versions of the library can be found on [GitHub](https://github.com/LordAkkarin/NBT/releases).

Issues
------

You encountered problems with the library or have a suggestion? Create an issue!

1. Make sure your issue has not been fixed in a newer version (check the list of [closed issues](https://github.com/LordAkkarin/NBT/issues?q=is%3Aissue+is%3Aclosed)
1. Create [a new issue](https://github.com/LordAkkarin/NBT/issues/new) from the [issues page](https://github.com/LordAkkarin/NBT/issues)
1. Enter your issue's title (something that summarizes your issue) and create a detailed description containing:
   - What is the expected result?
   - What problem occurs?
   - How to reproduce the problem?
   - Crash Log (Please use a [Pastebin](http://www.pastebin.com) service)
1. Click "Submit" and wait for further instructions

Building
--------

1. Clone this repository via ```git clone https://github.com/LordAkkarin/NBT.git``` or download a [zip](https://github.com/LordAkkarin/NBT/archive/master.zip)
1. Build the modification by running ```mvn clean install```
1. The resulting jars can be found in their respective ```target``` directories as well as your local maven repository

Contributing
------------

Before you add any major changes to the library you may want to discuss them with us (see [Contact](#contact)) as
we may choose to reject your changes for various reasons. All contributions are applied via [Pull-Requests](https://help.github.com/articles/creating-a-pull-request).
Patches will not be accepted. Also be aware that all of your contributions are made available under the terms of the
[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt). Please read the [Contribution Guidelines](CONTRIBUTING.md)
for more information.
