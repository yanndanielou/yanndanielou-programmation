#ifndef _MULTIRESOLUTION_H_
#define _MULTIRESOLUTION_H_

#include <FGraphics.h>

class ICoordinateFactory;
ICoordinateFactory* CoordFac();

class ICoordinateFactory
{
public:
	virtual ~ICoordinateFactory(){}
	virtual Osp::Graphics::Point		MakePoint(int x, int y) = 0;
	virtual Osp::Graphics::Rectangle	MakeRectangle(int x, int y, int w, int h) = 0;
	virtual Osp::Graphics::Dimension	MakeDimension(int w, int h) = 0;
	virtual int 						MakeScalar(int s)= 0;
};

inline Osp::Graphics::Point     __P(int x, int y)				{ return CoordFac()->MakePoint(x, y); 			 }
inline Osp::Graphics::Rectangle __R(int x, int y, int w, int h) { return CoordFac()->MakeRectangle(x, y, w, h);  }
inline Osp::Graphics::Dimension __D(int w, int h)				{ return CoordFac()->MakeDimension(w, h); 		 }
inline int 		 				__S(int s)						{ return CoordFac()->MakeScalar(s); 			 }

#endif /* _MULTIRESOLUTION_H_ */
