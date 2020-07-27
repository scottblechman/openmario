#OpenMario

## Level Data Specification

### Tiles
Each line of a level's `csv` file represents the contents of a single map tile on startup.
The columns represent properties of the map tile as follows:

_The list of tiles and their details here is non-exhaustive. New entries will be added as needed._

| Tile Type | Tile Label | X Position | Y Position | Tile-Specific Properties |
| --- | --- | --- | --- | --- |

Tiles can have the following types:
* Block
* Entity

Blocks can be labeled as one of the following:
* "ground"
* "brick"
* "question"

Entities can be labeled as one of the following:
* "goomba"
* "coin"
* "mushroom"
* "fireflower"
* "star"

When determining where map tiles exist, (0, 0) should be read as being at the bottom left of the map.
The x direction should be read as extending right, and the y direction as extending up. Note that
this is different from the physics coordinate system, which treats the y direction as extending down.

Each tile is offset from its neighbor by 1 when calculating x and y coordinates.

### Metadata
Each level file contains metadata that is inserted above the tile data.
A column describes a metadata property as follows:

_The list of properties and their details here is non-exhaustive. New entries will be added as needed._

| Property Key | Property Value |
| --- | --- |

The metadata currently available for a level is as follows:

| Key | Value |
| --- | --- |
| music | Background music for the level |
| nextlevel | The level to be loaded after ending the current level |

