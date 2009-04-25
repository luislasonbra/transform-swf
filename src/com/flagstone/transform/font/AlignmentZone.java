/*
 * AlignmentZone.java
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

package com.flagstone.transform.font;

import com.flagstone.transform.coder.CoderException;
import com.flagstone.transform.coder.Context;
import com.flagstone.transform.coder.SWFEncodeable;
import com.flagstone.transform.coder.SWFDecoder;
import com.flagstone.transform.coder.SWFEncoder;

public final class AlignmentZone implements SWFEncodeable
{
	private static final String FORMAT = "AlignmentZone: { coordinate=%f; range=%f }";
	
	private float coordinate;
	private float range;

	public AlignmentZone(SWFDecoder coder, Context context)
	{
		coordinate = coder.readHalf();
		range = coder.readHalf();
	}

	public AlignmentZone(float coordinate, float range)
	{
		this.coordinate = coordinate;
		this.range = range;
	}

	public float getCoordinate()
	{
		return coordinate;
	}

	public float getRange()
	{
		return range;
	}

	@Override
	public String toString()
	{
		return String.format(FORMAT,coordinate, range);
	}
	
	@Override
	public boolean equals(Object object) {
		boolean result;
		
		if (object == null) {
			result = false;
		} else if (object == this) {
			result = true;
		} else if (object instanceof AlignmentZone) {
			AlignmentZone zone = (AlignmentZone)object;
			result = coordinate == zone.coordinate &&
				range == zone.range;
		} else {
			result = false;
		}
		
		return result;
	}
	
	@Override
	public int hashCode() {
		return (Float.floatToIntBits(coordinate)*31) + Float.floatToIntBits(range);
	}
	
	public int prepareToEncode(final SWFEncoder coder, final Context context)
	{
		return 4;
	}

	public void encode(final SWFEncoder coder, final Context context) throws CoderException
	{
		coder.writeHalf(coordinate);
		coder.writeHalf(range);
	}
}