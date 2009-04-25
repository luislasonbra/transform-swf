/*
 * ActionObjectTest.java
 * Transform
 *
 * Copyright (c) 2009 Flagstone Software Ltd. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *  * Neither the name of Flagstone Software Ltd. nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.flagstone.transform.action;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import com.flagstone.transform.action.ActionObject;
import com.flagstone.transform.coder.CoderException;
import com.flagstone.transform.coder.Context;
import com.flagstone.transform.coder.SWFDecoder;
import com.flagstone.transform.coder.SWFEncoder;

@SuppressWarnings( { 
	"PMD.LocalVariableCouldBeFinal",
	"PMD.JUnitAssertionsShouldIncludeMessage" 
})
public final class ActionObjectTest {
	
	private transient final int type = 128;
	private transient final byte[] data = new byte[] {1,2,3,4};
	
	private transient ActionObject fixture;
	
	private transient final byte[] basic = new byte[] { (byte)0x01 };

	private transient final byte[] empty = new byte[] { (byte)0x80, 0x00, 0x00 };

	private transient final byte[] encoded = new byte[] { (byte)0x80, 0x04, 0x00, 
			0x01, 0x02, 0x03, 0x04};
	
	@Test(expected=IllegalArgumentException.class)
	public void checkAccessorForDataWithNull() {
		fixture = new ActionObject(type, null);
	}
	
	@Test
	public void checkCopy() {
		fixture = new ActionObject(type, data);
		ActionObject copy = fixture.copy();

		assertNotSame(fixture, copy);
		assertNotSame(fixture.getData(), copy.getData());
		assertEquals(fixture.toString(), copy.toString());
	}
	
	@Test
	public void encode() throws CoderException {		
		SWFEncoder encoder = new SWFEncoder(encoded.length);		
		Context context = new Context();
	
		fixture = new ActionObject(type, data);
		assertEquals(encoded.length, fixture.prepareToEncode(encoder, context));
		fixture.encode(encoder, context);
		
		assertTrue(encoder.eof());
		assertArrayEquals(encoded, encoder.getData());
	}
	
	@Test
	public void encodeBasic() throws CoderException {		
		SWFEncoder encoder = new SWFEncoder(basic.length);		
		Context context = new Context();

		fixture = new ActionObject(1);
		assertEquals(basic.length, fixture.prepareToEncode(encoder, context));
		fixture.encode(encoder, context);
		
		assertTrue(encoder.eof());
		assertArrayEquals(basic, encoder.getData());
	}
	
	@Test
	public void encodeEmpty() throws CoderException {		
		SWFEncoder encoder = new SWFEncoder(empty.length);		
		Context context = new Context();

		fixture = new ActionObject(type, new byte[0]);
		assertEquals(empty.length, fixture.prepareToEncode(encoder, context));
		fixture.encode(encoder, context);
		
		assertTrue(encoder.eof());
		assertArrayEquals(empty, encoder.getData());
	}
		
	@Test
	public void decode() throws CoderException {
		SWFDecoder decoder = new SWFDecoder(encoded);
		Context context = new Context();

		fixture = new ActionObject(decoder, context);
		
		assertTrue(decoder.eof());
		assertEquals(type, fixture.getType());
		assertArrayEquals(data, fixture.getData());
	}
	
	@Test
	public void decodeBasic() throws CoderException {
		SWFDecoder decoder = new SWFDecoder(basic);
		Context context = new Context();

		fixture = new ActionObject(decoder, context);
		
		assertTrue(decoder.eof());
		assertEquals(1, fixture.getType());
		assertArrayEquals(null, fixture.getData());
	}
	
	@Test
	public void decodeEmpty() throws CoderException {
		SWFDecoder decoder = new SWFDecoder(empty);
		Context context = new Context();

		fixture = new ActionObject(decoder, context);
		
		assertTrue(decoder.eof());
		assertEquals(type, fixture.getType());
		assertArrayEquals(new byte[0], fixture.getData());
	}
}