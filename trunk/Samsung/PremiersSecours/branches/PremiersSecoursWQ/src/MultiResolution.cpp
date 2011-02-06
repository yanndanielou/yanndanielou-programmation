#include "MultiResolution.h"
#include <FSystem.h>
#include <FBase.h>
#include <FApp.h>
#include <FUi.h>

using namespace Osp::Graphics;
using namespace Osp::Base;
using namespace Osp::System;
using namespace Osp::App;
using namespace Osp::Ui::Controls;

//--------------------------------------------------------------------------
//	WVGA
//--------------------------------------------------------------------------
class CoordinateFactoryWVGA : public ICoordinateFactory
{
public:
	virtual Osp::Graphics::Point		MakePoint(int x, int y);
	virtual Osp::Graphics::Rectangle	MakeRectangle(int x, int y, int w, int h);
	virtual Osp::Graphics::Dimension	MakeDimension(int w, int h);
	virtual int 						MakeScalar(int s);
};


Point CoordinateFactoryWVGA::MakePoint(int x, int y)
{
	return Point(x, y);
}

Rectangle CoordinateFactoryWVGA::MakeRectangle(int x, int y, int w, int h)
{
	return Rectangle(x, y, w, h);
}

Dimension CoordinateFactoryWVGA::MakeDimension(int w, int h)
{
	return Dimension(w, h);
}

int CoordinateFactoryWVGA::MakeScalar(int s)
{
	return s;
}





//--------------------------------------------------------------------------
//	WQVGA
//--------------------------------------------------------------------------
class CoordinateFactoryWQVGA : public ICoordinateFactory
{
public:
	virtual Osp::Graphics::Point		MakePoint(int x, int y);
	virtual Osp::Graphics::Rectangle	MakeRectangle(int x, int y, int w, int h);
	virtual Osp::Graphics::Dimension	MakeDimension(int w, int h);
	virtual int 						MakeScalar(int s);
};

Point CoordinateFactoryWQVGA::MakePoint(int x, int y)
{
	return Point(x/2, y/2);
}

Rectangle CoordinateFactoryWQVGA::MakeRectangle(int x, int y, int w, int h)
{
	return Rectangle(x/2, y/2, w/2, h/2);
}

Dimension CoordinateFactoryWQVGA::MakeDimension(int w, int h)
{
	return Dimension(w/2, h/2);
}

int CoordinateFactoryWQVGA::MakeScalar(int s)
{
	return s/2;
}


namespace
{
	template<typename T>
	class AutoRelease
	{
	public:
		AutoRelease(T* pT = 0):__pT(pT){}
		~AutoRelease(){	delete __pT; }
		T* GetPointer(){ return __pT; }
		const T* GetPointer() const{ return __pT; }
		void SetPointer(T* pT)
		{
			delete  __pT;
			__pT = pT;
		}
	private:
		T* __pT;
	};

	ICoordinateFactory* CreateCoordinateFactory()
	{
		Frame* pFrame = Application::GetInstance()->GetAppFrame()->GetFrame();
		if(pFrame == null)
			return null;

		Dimension dim = pFrame->GetSize();

		if (dim.width == 480 && dim.height == 800)
		{
			return new CoordinateFactoryWVGA();
		}
		else if (dim.width == 240 && dim.height == 400)
		{
			return new CoordinateFactoryWQVGA();
		}
		else
			return null;
	}
}; //namespace


//--------------------------------------------------------------------------
//	ICoordinateFactory
//--------------------------------------------------------------------------
ICoordinateFactory* CoordFac()
{
	static AutoRelease<ICoordinateFactory> coordFactory;
	if(coordFactory.GetPointer() == 0)
	{
		ICoordinateFactory* pFac = CreateCoordinateFactory();
		if(pFac == 0)
			AppLogException("CoordinateFactory has not initialized yet.");
		coordFactory.SetPointer(pFac);
	}

	return coordFactory.GetPointer();
}
