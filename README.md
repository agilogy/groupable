# groupable

[![Build Status](https://travis-ci.org/agilogy/groupable.svg)](https://travis-ci.org/agilogy/groupable)
[![Coverage Status](https://coveralls.io/repos/agilogy/groupable/badge.svg)](https://coveralls.io/r/agilogy/groupable)

A group method on TraversableOnce (and subclasses) that relies on the element order and preserves it.

```
import com.agilogy.utils.Groupable._
val people: Seq[Person] = ???
val peopleByName:Seq[(Name,Seq[Person])] = people.group(by = _.name)
```

It behaves somewhat like the standard groupBy but:
- It relies on the order of the traversable creating a group for each consecutive element that returns the same group key
- It preserves the order of the returned groups
- It returns a Seq of pairs key -> Seq(value) instead of a Map. 
  - I could have used a ListMap, but the seq makes it evident that lookup time is linear.

The method is added to TraversableOnce and subclasses via [Pimp my Library Pattern](http://www.artima.com/weblogs/viewpost.jsp?thread=179766).

## Installation

```
resolvers += Resolver.url("Agilogy Scala",url("http://dl.bintray.com/agilogy/scala/"))(Resolver.ivyStylePatterns)

libraryDependencies += "com.agilogy" %% "groupable" % "1.0.1"
```

## Usage

Import Groupable._:

```
import com.agilogy.utils.Groupable._
```

Now, two new methods `group` are available on TraversableOnce[T]:

```
def group[GT, RT](by: ((T) => GT)): Seq[(GT, Seq[T])]
def group[GT, RT](by: (T) ⇒ GT, as: (T) ⇒ RT): Seq[(GT, Seq[RT])]
```

Example (from the tests):

```
it should "group mapping values" in {
  val s = Seq("email" -> "j@example.com", "email" -> "k@example.com", "name" -> "John")
  val res = s.group(by = _._1, as = _._2)
  assert(res === Seq("email" -> Seq("j@example.com", "k@example.com"), "name" -> Seq("John")))
}
```

## Roadmap

- Maybe we could use a typeclass so that not only `TraversableOne`s are groupables. As an example, it could be interesting for `ResultSet`s.

## Copyright

Copyright 2015 Agilogy

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.