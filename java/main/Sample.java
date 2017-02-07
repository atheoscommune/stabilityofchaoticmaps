package main;

import java.io.*;
import java.awt.Frame;
import java.awt.FileDialog;
import java.util.Enumeration;

import org.rosuda.JRI.Rengine;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RList;
import org.rosuda.JRI.RVector;
import org.rosuda.JRI.RMainLoopCallbacks;

public class Sample {
	public static void main(String[] args) {
		Rengine re = RObjectLoader.getRObject();
		re.end();
	}

}