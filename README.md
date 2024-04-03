# Table of Contents

1.  [Eth CLJ Build λ](#org4b711f6)
2.  [TLDR / FAQ](#org5f6c452)
    1.  [What is this?](#org3c7eb3c)
    2.  [Why did you build this?](#org935d334)
    3.  [Why is it interesting?](#orgd6067f5)
    4.  [Why should this exist?](#orgf0a319e)
    5.  [How can this fit into my development process?](#org2eccaf8)
3.  [So what exactly did I build](#orgb7e36b5)
    1.  [You know about eth.build but don&rsquo;t know lisp](#org68b4f48)
        1.  [What is a cell?](#orgd537525)



<a id="org4b711f6"></a>

# Eth CLJ Build λ

A reflective, interactive playground for learning hard concepts in an intuitive way.

This project is pretty experimental and as such is subject to change.

This README also isn&rsquo;t finished, but in the sake of shipping I am just yoloing it out in it&rsquo;s ugly state.
But if you are the target audience right now, you should be able to understand what the concepts are and why it is interesting.


<a id="org5f6c452"></a>

# TLDR / FAQ


<a id="org3c7eb3c"></a>

## What is this?

I built a visual programming environment, that was intentionally designed for the end users to hack on, to fit their needs. Think something like video games that allow modding.


<a id="org935d334"></a>

## Why did you build this?

I built this to provide a way to allow people to build playgrounds that can explain difficult concepts, in a way that allows for visual representations, and allows the user to experiment and play with the concept to better understand it.


<a id="orgd6067f5"></a>

## Why is it interesting?

It is interesting because I haven&rsquo;t seen an educational tool be built in a way like this, that allows end user customization to the nth degree.


<a id="orgf0a319e"></a>

## Why should this exist?

Crypto has a lot of non intuitive topics that need to be deeply understood by people. Tools such as eth.build are perfect for exploring the introductory concepts such as private keys and hash functions. But tools like this fail when trying to build a demo of more complicated topics the creator didn&rsquo;t intend on demoing.


<a id="org2eccaf8"></a>

## How can this fit into my development process?

The most obvious way I see this fitting into the development process, is as a tool to quickly build interactive prototypes for ideas, or as a way of building simple interfaces for existing projects.


<a id="orgb7e36b5"></a>

# So what exactly did I build

This question is best answered with some context, so I will provide a few answers based on where you are coming from


<a id="org68b4f48"></a>

## You know about eth.build but don&rsquo;t know lisp

Fundementally, everything works the same as in eth.build.

You have nodes, they do things, they can send data to other nodes when you connect them.

Cool!

But the really exciting difference is present in the actual implementation of the nodes. Unlike eth.build, where each node is written as literal js code, and shipped as part of the application. There is only a single type of node in clj build!

And this type of node is called a **cell**!

Let&rsquo;s break this down and explain why this is so cool


<a id="orgd537525"></a>

### What is a cell?

The name means roughly the same as it does in biology, where it is a term for a generic &ldquo;thing&rdquo;, that can be programmed via DNA to fill a more specialized purpose.

In this app, all things follow this same principle.

Each node is capable of executing arbitrary code in a little sandbox, where the code is written in an extremely simple language called Clojure λ.

What this means, is that adding a new feature into clj build is as simple as:

1.  Creating a new cell
2.  Modifying it&rsquo;s code
3.  Immediately start playing with your new cell!

Because the only difference between any two nodes is the internal code, it unlocks a few really cool things:

1.  Sharing &ldquo;packages&rdquo; of cells becomes extremely easy.
2.  If you want to modify how a cell operates or add a feature to a cell, you can just directly break it open, change it&rsquo;s code, then continue hacking! Now that&rsquo;s extensible!

1.  BUT WAIT THERE&rsquo;S MORE! (cells with custom interfaces)

    The clojure ecosystem loves something called &ldquo;hiccup&rdquo;, which is a particular data format extremely similar to HTML or XML.
    
    It is just a simple way to represent a tree, in data. And because it&rsquo;s simply data, a cell can include functionality that outputs a hiccup data format. And what this means, is that a cell can describe how to display it&rsquo;s own interface!
    
    But before I give an example of this, let&rsquo;s compare html and hiccup really quickly.
    
    To write a simple list in html, we can do this:
    
        <ul>
            <li>Hello</li>
            <li>World!</li>
        </ul>
    
    So this corresponds to a unordered list node, which contains 2 children, the list item nodes, which each contain children of text.
    
    To represent the same thing in hiccup we can do this:
    
        [:ul
          [:li "Hello"]
          [:li "World!"]]
    
    This is the exact same thing, where we have a unordered list, which contains two children, which each contain some text as their children.

2.  But I want javascript package XYZ?!?

    Good news nerd! You can have that!
    
    The way cells operate under the hood, is something called &ldquo;sci&rdquo;. Which is a &ldquo;small clojure interpreter&rdquo;.
    
    And because this is running in javascript, you can have full interop with any javascript package you could need. All it takes is simply injecting it into sci!
    
    The way you interact with the library might take a bit of getting used to, but it is really easy.

