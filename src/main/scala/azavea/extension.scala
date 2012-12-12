package azavea.slick

import language.implicitConversions

import scala.slick.ast.{Library, Node}
import scala.slick.lifted.{Column, ExtensionMethods}

import com.vividsolutions.jts.geom._
import azavea.slick.PostgisDriver._

object PostgisLibrary {
  val Distance = new Library.SqlFunction("ST_Distance")
}

final class GeometryColumnExtensionMethods[P1](val c: Column[P1]) extends AnyVal with ExtensionMethods[Geometry, P1] {
  def distance[P2,R](e: Column[P2])(implicit om: o#arg[Geometry, P2]#to[Double, R]) =
    om(PostgisLibrary.Distance.column(n, Node(e)))
}

trait PostgisConversions {
  implicit def geometryColumnExtensionMethods(c: Column[Geometry]) = new GeometryColumnExtensionMethods[Geometry](c)
  implicit def geometryColumnOptionExtensionMethods(c: Column[Option[Geometry]]) = new GeometryColumnExtensionMethods[Option[Geometry]](c)
}